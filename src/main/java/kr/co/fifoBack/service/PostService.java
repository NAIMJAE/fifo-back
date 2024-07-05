package kr.co.fifoBack.service;

import com.querydsl.core.Tuple;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.PageResponseDTO;
import kr.co.fifoBack.dto.post.CommentDTO;
import kr.co.fifoBack.dto.post.CommentHeartDTO;
import kr.co.fifoBack.dto.post.HeartDTO;
import kr.co.fifoBack.dto.post.PostDTO;
import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.entity.post.*;
import kr.co.fifoBack.repository.post.*;
import kr.co.fifoBack.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final TagsRepository tagsRepository;
    private final FileRepository fileRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;
    private final CommentHeartRepository commentHeartRepository;
    private final PostHitRepository postHitRepository;
    private final UserRepository userRepository;
    private final HttpServletRequest httpServletRequest;
    private final HelperService helperService;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    // 게시글 작성
    @Transactional
    public ResponseEntity<?> insertPost(PostDTO postDTO){
        Post post = modelMapper.map(postDTO, Post.class);
        Post savedPost = postRepository.save(post);

        // 이미지 주소 대체
        String replacedContent = savedPost.getContent().replace("$#@^", Integer.toString(savedPost.getPno()));
        savedPost.setContent(replacedContent);
        postRepository.save(post);

        // 게시글 태그 저장
        for (String tag : postDTO.getTagName()) {
            // tags 저장
            Tags tags = new Tags();
            tags.setTag("#"+tag);
            Tags savedTags = tagsRepository.save(tags); // 태그 중복 저장 오류 해결해야함
            
            // PostTag 저장
            PostTag postTag = new PostTag();
            postTag.setPno(savedPost.getPno());
            postTag.setTno(savedTags.getTno());
            postTagRepository.save(postTag);
        }

        // 파일 저장
        if (postDTO.getFiles() != null) {
            List<String> filesNames = helperService.uploadFiles(postDTO.getFiles(), "/post/files/" + savedPost.getPno() + "/", false);
            for (int i=0; i<postDTO.getFiles().size(); i++) {
                File file = new File();
                file.setPno(savedPost.getPno());
                file.setSName(filesNames.get(i));
                file.setOName(postDTO.getFiles().get(i).getOriginalFilename());
                fileRepository.save(file);
            }
        }

        // 이미지 저장
        if (postDTO.getImages() != null) {
            helperService.uploadFiles(postDTO.getImages(), "post/images/" + savedPost.getPno() + "/", true);
        }

        return ResponseEntity.status(HttpStatus.OK).body(savedPost.getPno());
    }

    // 게시글 조회 + 검색
    public ResponseEntity<?> selectPostByKeyword(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("pno");
        Page<Tuple> pageTuple = null;
        if (pageRequestDTO.getType().equals("tag")) {
            pageTuple = postRepository.selectPostByTag(pageRequestDTO, pageable);
        }else {
            pageTuple = postRepository.selectPostByKeyword(pageRequestDTO, pageable);
        }

        List<PostDTO> postDTOList = pageTuple.getContent().stream()
                .map(tuple -> {
                    Post post = tuple.get(0, Post.class);
                    String thumb = tuple.get(1, String.class);
                    String nick = tuple.get(2, String.class);
                    PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                    postDTO.setThumb(thumb);
                    postDTO.setNick(nick);
                    return postDTO;
                }).toList();
        log.info("postDTOList : " + postDTOList);

        int total = (int) pageTuple.getTotalElements();

        List<PostDTO> resultPostList = new ArrayList<>();
        for (PostDTO each : postDTOList) {
            each.setTagName(postRepository.selectTagForPno(each.getPno()));
            resultPostList.add(each);
        }
        log.info("resultPostList : " + resultPostList);

        PageResponseDTO pagePostDTO = PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(resultPostList)
                .total(total)
                .build();

        log.info("pagePostDTO : " + pagePostDTO);

        return ResponseEntity.status(HttpStatus.OK).body(pagePostDTO);
    }

    // 게시글 보기
    public ResponseEntity<?> selectPost(int pno) {
        // 조회수 +1
        String ipAddress = httpServletRequest.getRemoteAddr();
        Optional<PostHit> optPostHit = postHitRepository.findByPnoAndAddress(pno, ipAddress);
        if (optPostHit.isEmpty()) {
            postRepository.incrementPostHit(pno);
            PostHit postHit = new PostHit();
            postHit.setAddress(ipAddress);
            postHit.setPno(pno);
            postHitRepository.save(postHit);
        }

        // 게시글 정보
        Tuple postTuple = postRepository.selectPost(pno);

        PostDTO postDTO = modelMapper.map(postTuple.get(0, Post.class), PostDTO.class);
        postDTO.setThumb(postTuple.get(1, String.class));
        postDTO.setNick(postTuple.get(2, String.class));
        postDTO.setCateName(postTuple.get(3, String.class));

        // 파일
        List<File> fileList = fileRepository.findByPno(postDTO.getPno());
        postDTO.setFileName(fileList.stream().map(file -> {
            LinkedHashMap<Integer, String> fileMap = new LinkedHashMap<>();
            fileMap.put(file.getFno(), file.getOName());
            return fileMap;
        }).toList());

        // 태그
        postDTO.setTagName(postRepository.selectTagForPno(postDTO.getPno()));

        return ResponseEntity.status(HttpStatus.OK).body(postDTO);
    }

    // 게시글 좋아요
    @Transactional
    public ResponseEntity<?> insertPostHeart(HeartDTO heartDTO) {
        Optional<Heart> alreadyHeart = heartRepository.findByUserNoAndPno(heartDTO.getUserNo(), heartDTO.getPno());

        if (alreadyHeart.isPresent()){
            // 좋아요 취소
            heartRepository.deleteById(alreadyHeart.get().getHNo());
            Optional<Post> optPost = postRepository.findById(heartDTO.getPno());
            optPost.get().setHeartNum(optPost.get().getHeartNum()-1);
            postRepository.save(optPost.get());
            return ResponseEntity.status(HttpStatus.OK).body(0);
        }else {
            // 좋아요 추가
            heartRepository.save(modelMapper.map(heartDTO, Heart.class));
            Optional<Post> optPost = postRepository.findById(heartDTO.getPno());
            optPost.get().setHeartNum(optPost.get().getHeartNum()+1);
            postRepository.save(optPost.get());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
    }

    // 게시글 수정
    @Transactional
    public ResponseEntity<?> modifyPost(PostDTO postDTO) {

        Optional<Post> optPost = postRepository.findById(postDTO.getPno());

        if (optPost.isPresent()) {
            // 이미지 주소 대체
            String replacedContent = postDTO.getContent().replace("$#@^", Integer.toString(optPost.get().getPno()));
            optPost.get().setContent(replacedContent);
            optPost.get().setCateNo(postDTO.getCateNo());
            optPost.get().setContent(replacedContent);
            optPost.get().setTitle(postDTO.getTitle());
            optPost.get().setModiDate(LocalDateTime.now());
            Post savedPost = postRepository.save(optPost.get());
        }

        // 기존 태그 삭제
        List<PostTag> postTagList = postTagRepository.findByPno(postDTO.getPno());
        log.info("postTagList : " + postTagList);
        postTagRepository.deleteByPno(postDTO.getPno());
        for (PostTag tag : postTagList) {
            log.info("tag : " + tag);
            tagsRepository.deleteById(tag.getTno());
        }
        // DB 새로고침
        entityManager.flush();

        // 게시글 태그 저장
        for (String tag : postDTO.getTagName()) {
            // tags 저장
            Tags tags = new Tags();
            tags.setTag("#"+tag);
            Tags savedTags = tagsRepository.save(tags); // 태그 중복 저장 오류 해결해야함

            // PostTag 저장
            PostTag postTag = new PostTag();
            postTag.setPno(postDTO.getPno());
            postTag.setTno(savedTags.getTno());
            postTagRepository.save(postTag);
        }

        // 파일 저장
        if (postDTO.getFiles() != null) {
            List<String> filesNames = helperService.uploadFiles(postDTO.getFiles(), "/post/files/" + postDTO.getPno() + "/", false);
            for (int i=0; i<postDTO.getFiles().size(); i++) {
                File file = new File();
                file.setPno(postDTO.getPno());
                file.setSName(filesNames.get(i));
                file.setOName(postDTO.getFiles().get(i).getOriginalFilename());
                fileRepository.save(file);
            }
        }

        // 파일 삭제
        if (postDTO.getDeleteFile().length > 0) {
            for (int fno : postDTO.getDeleteFile()) {
                Optional<File> optFile = fileRepository.findById(fno);
                helperService.deleteFiles("/post/files/" + postDTO.getPno() + "/", optFile.get().getSName());
                fileRepository.deleteById(fno);
            }
        }

        // 이미지 저장
        if (postDTO.getImages() != null) {
            helperService.uploadFiles(postDTO.getImages(), "post/images/" + postDTO.getPno() + "/", true);
        }

        return ResponseEntity.status(HttpStatus.OK).body(postDTO.getPno());
    }

    // 게시글 삭제
    @Transactional
    public ResponseEntity<?> deletePost(int pno) {

        // 게시글 좋아요 삭제 heart
        heartRepository.deleteByPno(pno);

        // 댓글 좋아요 삭제 commentheart
        commentHeartRepository.deleteByPno(pno);

        // 댓글 삭제 comment
        commentRepository.deleteByPno(pno);

        // 태그 삭제 tags  posttag
        List<PostTag> postTagList = postTagRepository.findByPno(pno);
        postTagRepository.deleteByPno(pno);
        for (PostTag tag : postTagList) {
            tagsRepository.deleteById(tag.getTno());
        }

        // 이미지 삭제
        helperService.deleteFileDirectory("post/images/" + pno);

        // 파일 삭제 file
        helperService.deleteFileDirectory("post/files/" + pno);
        fileRepository.deleteByPno(pno);

        // 게시글 삭제 post
        postRepository.deleteById(pno);

        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    // 게시글 첨부파일 다운로드
    public ResponseEntity<?> downloadFile(int fno) {

        Optional<File> optFile = fileRepository.findById(fno);

        if (optFile.isPresent()) {
            return helperService.fileDownloadHelp("post/files/" + optFile.get().getPno(), optFile.get().getSName(), optFile.get().getOName());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 댓글 작성
    @Transactional
    public ResponseEntity<?> insertComment(CommentDTO commentDTO) {
        commentDTO.setCreateDate(LocalDateTime.now());
        Comment comment = commentRepository.save(modelMapper.map(commentDTO, Comment.class));
        if (comment.getCno() != 0) {
            Optional<Post> optPost = postRepository.findById(comment.getPno());
            optPost.get().setComNum(optPost.get().getComNum() + 1);
            postRepository.save(optPost.get());

            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 댓글 불러오기
    @Transactional
    public ResponseEntity<?> selectComment(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("no");
        Page<Tuple> commentTuple = commentRepository.selectCommentByPno(pageRequestDTO, pageable);

        if (commentTuple.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }else {
            List<CommentDTO> commentDTOList = commentTuple.getContent().stream().map(
                    tuple -> {
                        Comment comment = tuple.get(0, Comment.class);
                        String thumb = tuple.get(1, String.class);
                        String nick = tuple.get(2, String.class);
                        List<CommentDTO> reply = commentRepository.selectReply(comment.getPno(), comment.getCno());
                        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                        commentDTO.setThumb(thumb);
                        commentDTO.setNick(nick);
                        commentDTO.setReplyList(reply);
                        return commentDTO;
                    }).toList();
            int total = (int) commentTuple.getTotalElements();
            PageResponseDTO pageResponseDTO = PageResponseDTO.builder()
                    .pageRequestDTO(pageRequestDTO)
                    .dtoList(commentDTOList)
                    .total(total)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(pageResponseDTO);
        }
    }

    // 댓글 수정
    @Transactional
    public ResponseEntity<?> modifyComment(CommentDTO commentDTO) {
        Optional<Comment> optComment = commentRepository.findById(commentDTO.getCno());

        if (optComment.isPresent()) {
            optComment.get().setContent(commentDTO.getContent());
            optComment.get().setUpdateDate(LocalDateTime.now());
            commentRepository.save(optComment.get());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 댓글 삭제
    public ResponseEntity<?> deleteComment(int cno) {
        Optional<Comment> optComment = commentRepository.findById(cno);
        if (optComment.isPresent()) {
            optComment.get().setUpdateDate(LocalDateTime.now());
            optComment.get().setContent("삭제되었습니다.");
            optComment.get().setState(1);
            commentRepository.save(optComment.get());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(0);
        }
    }

    // 댓글 좋아요
    public ResponseEntity<?> commentHeart(CommentHeartDTO commentHeartDTO) {
        Optional<CommentHeart> alreadyHeart = commentHeartRepository.findByUserNoAndCno(commentHeartDTO.getUserNo(), commentHeartDTO.getCno());

        if (alreadyHeart.isPresent()){
            // 좋아요 취소
            commentHeartRepository.deleteById(alreadyHeart.get().getCheartno());
            Optional<Comment> optComment = commentRepository.findById(commentHeartDTO.getCno());
            optComment.get().setHeart(optComment.get().getHeart()-1);
            commentRepository.save(optComment.get());
            return ResponseEntity.status(HttpStatus.OK).body(0);
        }else {
            // 좋아요 추가
            commentHeartRepository.save(modelMapper.map(commentHeartDTO, CommentHeart.class));
            Optional<Comment> optComment = commentRepository.findById(commentHeartDTO.getCno());
            optComment.get().setHeart(optComment.get().getHeart()+1);
            commentRepository.save(optComment.get());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
    }
}
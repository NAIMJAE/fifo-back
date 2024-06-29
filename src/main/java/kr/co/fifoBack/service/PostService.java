package kr.co.fifoBack.service;

import com.querydsl.core.Tuple;
import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.PageResponseDTO;
import kr.co.fifoBack.dto.post.PostDTO;
import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.entity.post.File;
import kr.co.fifoBack.entity.post.Post;
import kr.co.fifoBack.entity.post.PostTag;
import kr.co.fifoBack.entity.post.Tags;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final TagsRepository tagsRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final HelperService helperService;
    private final ModelMapper modelMapper;


    // 게시글 작성
    public ResponseEntity<?> insertPost(PostDTO postDTO){
        Post post = modelMapper.map(postDTO, Post.class);
        log.info("post : " + post);
        Post savedPost = postRepository.save(post);
        String[] tagArr = postDTO.getTag().split(" ");
        log.info("tagArr : " + tagArr.toString());

        // 게시글 태그 저장
        for (String tag : tagArr) {
            // tags 저장
            Tags tags = new Tags();
            tags.setTag(tag);
            Tags savedTags = tagsRepository.save(tags); // 태그 중복 저장 오류 해결해야함
            
            // PostTag 저장
            PostTag postTag = new PostTag();
            postTag.setPno(savedPost.getPno());
            postTag.setTno(savedTags.getTno());
            postTagRepository.save(postTag);
        }

        // 파일 저장
        List<String> filesNames = helperService.uploadFiles(postDTO.getFiles(), "/post/files/", false);
        for (int i=0; i<postDTO.getFiles().size(); i++) {
            File file = new File();
            file.setPno(savedPost.getPno());
            file.setSName(filesNames.get(i));
            file.setOName(postDTO.getFiles().get(i).getOriginalFilename());
            fileRepository.save(file);
        }

        // 이미지 저장 (DB 저장 필요 없음)
        helperService.uploadFiles(postDTO.getImages(), "post/images/", true);

        return ResponseEntity.status(HttpStatus.OK).body(savedPost.getPno());
    }

    // 게시글 조회 + 검색
    public ResponseEntity<?> selectPostByKeyword(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("pno");
        Page<Tuple> pageTuple = postRepository.selectPostByKeyword(pageRequestDTO, pageable);

        List<PostDTO> postDTOList = pageTuple.getContent().stream()
                .map(tuple -> {
                    Post post = tuple.get(0, Post.class);
                    String thumb = tuple.get(1, String.class);
                    PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                    postDTO.setThumb(thumb);
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
        // 게시글 정보
        Tuple postTuple = postRepository.selectPost(pno);

        PostDTO postDTO = modelMapper.map(postTuple.get(0, Post.class), PostDTO.class);
        postDTO.setThumb(postTuple.get(1, String.class));
        postDTO.setCateName(postTuple.get(2, String.class));

        // 파일
        List<File> fileList = fileRepository.findByPno(postDTO.getPno());
        postDTO.setFileName(fileList.stream().map(file -> file.getOName()).toList());

        // 태그
        postDTO.setTagName(postRepository.selectTagForPno(postDTO.getPno()));

        // 댓글


        return ResponseEntity.status(HttpStatus.OK).body(postDTO);
    }


}

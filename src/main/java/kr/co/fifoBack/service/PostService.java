package kr.co.fifoBack.service;

import kr.co.fifoBack.dto.PostDTO;
import kr.co.fifoBack.entity.File;
import kr.co.fifoBack.entity.Post;
import kr.co.fifoBack.entity.PostTag;
import kr.co.fifoBack.entity.Tags;
import kr.co.fifoBack.repository.FileRepository;
import kr.co.fifoBack.repository.PostRepository;
import kr.co.fifoBack.repository.PostTagRepository;
import kr.co.fifoBack.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final TagsRepository tagsRepository;
    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;
    private final HelperService helperService;

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
        for (String name : filesNames) {
            File file = new File();
            file.setPno(savedPost.getPno());
            file.setSName(name);
            fileRepository.save(file);
        }

        // 이미지 저장 (DB 저장 필요 없음)
        helperService.uploadFiles(postDTO.getImages(), "post/images/", true);

        return ResponseEntity.status(HttpStatus.OK).body(savedPost.getPno());
    }





}

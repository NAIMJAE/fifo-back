package kr.co.fifoBack.controller;

import kr.co.fifoBack.dto.PostDTO;
import kr.co.fifoBack.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    
    // 게시글 작성
    @PostMapping("/post/write")
    public ResponseEntity<?> postWrite(PostDTO postDTO) {
        log.info("postDTO : " + postDTO);

        return postService.insertPost(postDTO);
    }

}

package kr.co.fifoBack.controller;

import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.post.CommentDTO;
import kr.co.fifoBack.dto.post.PostDTO;
import kr.co.fifoBack.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    
    // 게시글 작성
    @PostMapping("/post")
    public ResponseEntity<?> postWrite(PostDTO postDTO) {
        log.info("postDTO : " + postDTO);

        return postService.insertPost(postDTO);
    }

    // 게시글 목록 조회 + 검색
    @PostMapping("/post/list")
    public ResponseEntity<?> postList(@RequestBody PageRequestDTO pageRequestDTO) {
        log.info("pageRequestDTO : " + pageRequestDTO);

        return postService.selectPostByKeyword(pageRequestDTO);
    }

    // 게시글 조회
    @GetMapping("/post/{pno}")
    public ResponseEntity<?> postView(@PathVariable("pno") int pno) {
        log.info("pno : " + pno);
        return postService.selectPost(pno);
    }

    // 댓글 작성
    @PostMapping("/comment")
    public ResponseEntity<?> commentWrite(@RequestBody CommentDTO commentDTO) {
        log.info("commentDTO : " + commentDTO);

        return null;
    }
}

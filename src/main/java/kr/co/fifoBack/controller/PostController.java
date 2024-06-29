package kr.co.fifoBack.controller;

import kr.co.fifoBack.dto.PageRequestDTO;
import kr.co.fifoBack.dto.post.CommentDTO;
import kr.co.fifoBack.dto.post.HeartDTO;
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

    // 게시글 좋아요
    @PostMapping("/post/heart")
    public ResponseEntity<?> postHeart(@RequestBody HeartDTO heartDTO) {
        log.info("heartDTO : " + heartDTO);
        return postService.insertPostHeart(heartDTO);
    }

    // 댓글 작성
    @PostMapping("/comment")
    public ResponseEntity<?> commentWrite(@RequestBody CommentDTO commentDTO) {
        log.info("commentDTO : " + commentDTO);
        return postService.insertComment(commentDTO);
    }

    // 댓글 불러오기
    @GetMapping("/comment/{pno}")
    public ResponseEntity<?> commentView(@PathVariable("pno") int pno) {
        log.info("pno : " + pno);
        return postService.selectComment(pno);
    }

    // 댓글 수정
    @PatchMapping("/comment")
    public ResponseEntity<?> commentModify(@RequestBody CommentDTO commentDTO) {
        log.info("commentDTO : " + commentDTO);
        return postService.modifyComment(commentDTO);
    }
}

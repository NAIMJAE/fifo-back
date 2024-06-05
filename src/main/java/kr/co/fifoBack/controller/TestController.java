package kr.co.fifoBack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TestController {

    @PostMapping("/test")
    public ResponseEntity<?> test() {
        log.info("Server ON!");
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }
}

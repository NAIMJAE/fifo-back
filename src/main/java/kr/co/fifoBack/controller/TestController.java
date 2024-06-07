package kr.co.fifoBack.controller;

import kr.co.fifoBack.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TestController {

    private final TestService testService;

    @PostMapping("/test")
    public ResponseEntity<?> test() {
        log.info("Server ON!");
        return testService.selectTest();
    }
}

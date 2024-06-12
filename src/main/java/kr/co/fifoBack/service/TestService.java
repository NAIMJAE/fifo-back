package kr.co.fifoBack.service;

import kr.co.fifoBack.dto.post.TestDTO;
import kr.co.fifoBack.entity.post.Test;
import kr.co.fifoBack.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TestService {

    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<?> selectTest() {

        Optional<Test> optTest = testRepository.findById("abcd1234");
        if (optTest.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(optTest.get(), TestDTO.class));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}

package kr.co.fifoBack.service;

import kr.co.fifoBack.dto.user.TermsDTO;
import kr.co.fifoBack.entity.user.Terms;
import kr.co.fifoBack.repository.user.TermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TermsService {
    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;
    public ResponseEntity<?> getTerms(){
        String terms = termsRepository.getTerms();
        log.info(terms);

        return ResponseEntity.ok().body(terms);

    }
    public ResponseEntity<?> getPrivacy(){
        String privacy = termsRepository.getPrivacy();
        log.info(privacy);

        return ResponseEntity.ok().body(privacy);
    }
}

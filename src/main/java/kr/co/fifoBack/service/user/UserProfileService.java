package kr.co.fifoBack.service.user;

import kr.co.fifoBack.dto.user.RegionDTO;
import kr.co.fifoBack.dto.user.SkillDTO;
import kr.co.fifoBack.dto.user.UserRegionDTO;
import kr.co.fifoBack.dto.user.UsersDTO;
import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.entity.user.Region;
import kr.co.fifoBack.entity.user.Skill;
import kr.co.fifoBack.entity.user.UserRegion;
import kr.co.fifoBack.mapper.UsersMapper;
import kr.co.fifoBack.repository.grade.LanguageRepository;
import kr.co.fifoBack.repository.user.RegionRepository;
import kr.co.fifoBack.repository.user.SkillRepository;
import kr.co.fifoBack.repository.user.UserRegionRepository;
import kr.co.fifoBack.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final LanguageRepository languageRepository;
    private final RegionRepository regionRepository;
    private final UserRegionRepository userRegionRepository;
    private final UsersMapper usersMapper;

    /**유저 정보 가져오기*/
    public ResponseEntity<?> getProfile(int userno){
        Optional<Users> user = userRepository.findById(userno);
        List<Skill> skillList = skillRepository.findByUserno(userno);

        if(user.isPresent()){
            UsersDTO usersDTO = modelMapper.map(user, UsersDTO.class);

            String[] languageNames = skillList.stream()
                    .map(Skill::getLanguagename)
                    .toArray(String[]::new);

            Integer [] levels = skillList.stream()
                    .map(Skill::getLevel)
                    .toArray(Integer[]::new);

            usersDTO.setLanguagename(languageNames);
            usersDTO.setLevels(levels);

            return ResponseEntity.ok().body(usersDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }

    }

    /**내 정보 수정*/
    public ResponseEntity<?> updateProfile(int userno, String type, String information){

        try {
            usersMapper.updateProfile(userno, type, information);
            return ResponseEntity.ok().body(true);
        }catch (Exception e){
            return ResponseEntity.ok().body(false);
        }

    }

    /**지역 가져오기*/
    public ResponseEntity<?> selectRegion(){
        List<Region> regions = regionRepository.findAll();
        log.info("@@@" + regions);
        if(regions.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("NOT FOUND");
        }else{
            List<RegionDTO> regionDTOS = regions.stream()
                    .map(region -> modelMapper.map(region, RegionDTO.class))
                    .collect(Collectors.toList());

            log.info("@@@" + regionDTOS);

            return ResponseEntity.ok().body(regionDTOS);
        }
    }
    
    /**내 지역 가져오기*/
    public ResponseEntity<?> selectMyRegion(int userno){
        if(userno<0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT FOUND");
        }else{
            List<UserRegion> userRegions = userRegionRepository.findByUserno(userno);
            return ResponseEntity.ok().body(userRegions);
        }
    }
    
    /**지역 수정하기*/
    public ResponseEntity<?> addRegion(int userno, String[] regionArr){

        log.info("@@@...1 : " +userno);
        log.info("@@@...2 : " + Arrays.toString(regionArr));

        List<UserRegionDTO> userRegionDTOs = new ArrayList<>();

        for(String regionname : regionArr){
            log.info("@@여기여기" + regionname);
            UserRegionDTO userRegionDTO = new UserRegionDTO();
            userRegionDTO.setUserno(userno);
            userRegionDTO.setRegionname(regionname);

            userRegionDTOs.add(userRegionDTO);
        }

        log.info("@@@...3" + userRegionDTOs);

        if(userRegionDTOs.isEmpty()){
            log.info("@@@...4");
            return ResponseEntity.ok().body(false);
        }else{
            List<UserRegion> userRegions = userRegionDTOs.stream()
                    .map(region->modelMapper.map(region, UserRegion.class))
                    .collect(Collectors.toList());

            List<UserRegion> result = userRegionRepository.saveAll(userRegions);
            log.info("@@@...5" + result);

            return ResponseEntity.ok().body(true);
        }

    }

    /**언어 중복 제거해서 가져오기*/
    public ResponseEntity<?> getDistinctLanguage(int userno){
        List<String> result =languageRepository.getDistinctLanguage(userno);
        log.info("여기야@@@@"+result);
        if (result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }else{
            return ResponseEntity.ok().body(result);
        }
    }

    /**선택한 언어 추가하기*/
    public ResponseEntity<?> addSkill(int userno, String[] inputSkill){

        for(String skills : inputSkill ){
            SkillDTO skillDTO = new SkillDTO();

            skillDTO.setUserno(userno);
            skillDTO.setLanguagename(skills);
            skillDTO.setLevel(1);

            Skill skill = modelMapper.map(skillDTO, Skill.class);
            Skill result = skillRepository.save(skill);

            if(result.getLanguagename().isEmpty()){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR");
            }
        }

        return ResponseEntity.ok().body("저장");
    }

    /**기술스택 삭제*/
    public ResponseEntity<?> deleteSkill(int userno, String languagename){
        return null;
    }
}

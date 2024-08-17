package kr.co.fifoBack.service.user;

import jakarta.transaction.Transactional;
import kr.co.fifoBack.dto.user.*;
import kr.co.fifoBack.entity.Users;
import kr.co.fifoBack.entity.user.*;
import kr.co.fifoBack.mapper.ExperienceMapper;
import kr.co.fifoBack.mapper.UsersMapper;
import kr.co.fifoBack.repository.grade.LanguageRepository;
import kr.co.fifoBack.repository.user.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
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
    private final JobRepository jobRepository;
    private final ExperienceRepository experienceRepository;
    private final PasswordEncoder passwordEncoder;


    private final UsersMapper usersMapper;
    private final ExperienceMapper experienceMapper;

    private static final String DEFAULT_PROFILE_IMAGE = "ppoppi.png";

    @Value("uploads/user/")
    private String fileUploadPath;

    /**
     * 유저 정보 가져오기
     */
    public ResponseEntity<?> getProfile(int userno) {
        Optional<Users> user = userRepository.findById(userno);
        List<Skill> skillList = skillRepository.findByUserno(userno);
        log.info("요요" + skillList);
        if (user.isPresent()) {
            UsersDTO usersDTO = modelMapper.map(user, UsersDTO.class);

//            String[] languageNames = skillList.stream()
//                    .map(Skill::getLanguagename)
//                    .toArray(String[]::new);
//
//            Integer[] levels = skillList.stream()
//                    .map(Skill::getLevel)
//                    .toArray(Integer[]::new);
            List<SkillDTO> skillDTOS = skillList.stream()
                            .map(list->modelMapper.map(list, SkillDTO.class))
                            .toList();

            usersDTO.setSkillList(skillDTOS);

            return ResponseEntity.ok().body(usersDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }

    }

    /**
     * 내 정보 수정
     */
    public ResponseEntity<?> updateProfile(int userno, String type, String information) {

        try {
            usersMapper.updateProfile(userno, type, information);
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            return ResponseEntity.ok().body(false);
        }

    }

    /**
     * 프로필 업로드
     */
    @Transactional
    public ResponseEntity<?> uploadProfile(MultipartFile file, int userno) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.ok().body(false);
        } else {
            Optional<Users> users = userRepository.findById(userno);

            Map<String, String> oldFileMap = new HashMap<>();

            // users객체가 존재 할 때에만 실행
            users.ifPresent(user -> {
                UsersDTO dto = modelMapper.map(user, UsersDTO.class);
                dto.setThumb(user.getThumb());
                oldFileMap.put("thumb", dto.getThumb());
            });

            String oldFileName = oldFileMap.get("thumb");

            /**디폴트 프로필이 아니라면 삭제 후 삽입*/
            if (!oldFileName.equals(DEFAULT_PROFILE_IMAGE)) {

                File oldFile = new File(fileUploadPath, oldFileName);

                // 기존 파일 삭제
                oldFile.delete();

                String path = new File(fileUploadPath).getAbsolutePath();

                String thumb = file.getOriginalFilename();

                String base64 = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
                String extention = thumb.substring(thumb.lastIndexOf(".") + 1); // 확장자

                String newName = base64 + "." + extention;

                File uploadFile = new File(path, newName);

                // 파일 저장
                Thumbnails.of(file.getInputStream())
                        .size(250, 250)
                        .toFile(uploadFile);

                // 유저 정보 수정
                usersMapper.uploadProfile(userno, newName);
            } else {
                log.info("@@2번으로");
                String path = new File(fileUploadPath).getAbsolutePath();

                String thumb = file.getOriginalFilename();

                String base64 = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
                String extention = thumb.substring(thumb.lastIndexOf(".") + 1); // 확장자

                String newName = base64 + "." + extention;

                File uploadFile = new File(path, newName);

                // 파일 저장
                Thumbnails.of(file.getInputStream())
                        .size(250, 250)
                        .toFile(uploadFile);

                // 유저 정보 수정
                usersMapper.uploadProfile(userno, newName);
            }

            return ResponseEntity.ok().body(true);
        }
    }

    /**
     * 지역 가져오기
     */
    public ResponseEntity<?> selectRegion() {
        List<Region> regions = regionRepository.findAll();
        log.info("@@@" + regions);
        if (regions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("NOT FOUND");
        } else {
            List<RegionDTO> regionDTOS = regions.stream()
                    .map(region -> modelMapper.map(region, RegionDTO.class))
                    .collect(Collectors.toList());

            log.info("@@@" + regionDTOS);

            return ResponseEntity.ok().body(regionDTOS);
        }
    }

    /**
     * 내 지역 가져오기
     */
    public ResponseEntity<?> selectMyRegion(int userno) {
        if (userno < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT FOUND");
        } else {
            List<UserRegion> userRegions = userRegionRepository.findByUserno(userno);
            return ResponseEntity.ok().body(userRegions);
        }
    }

    /**
     * 지역 수정하기
     */
    public ResponseEntity<?> addRegion(int userno, String[] regionArr) {

        log.info("@@@...1 : " + userno);
        log.info("@@@...2 : " + Arrays.toString(regionArr));

        List<UserRegionDTO> userRegionDTOs = new ArrayList<>();

        for (String regionname : regionArr) {
            log.info("@@여기여기" + regionname);
            UserRegionDTO userRegionDTO = new UserRegionDTO();
            userRegionDTO.setUserno(userno);
            userRegionDTO.setRegionname(regionname);

            userRegionDTOs.add(userRegionDTO);
        }

        log.info("@@@...3" + userRegionDTOs);

        if (userRegionDTOs.isEmpty()) {
            log.info("@@@...4");
            return ResponseEntity.ok().body(false);
        } else {
            List<UserRegion> userRegions = userRegionDTOs.stream()
                    .map(region -> modelMapper.map(region, UserRegion.class))
                    .collect(Collectors.toList());

            List<UserRegion> result = userRegionRepository.saveAll(userRegions);
            log.info("@@@...5" + result);

            return ResponseEntity.ok().body(true);
        }

    }

    /**
     * 언어 중복 제거해서 가져오기
     */
    public ResponseEntity<?> getDistinctLanguage(int userno) {
        List<String> result = languageRepository.getDistinctLanguage(userno);
        log.info("여기야@@@@" + result);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    /**
     * 선택한 언어 추가하기
     */
    public ResponseEntity<?> addSkill(int userno, String[] inputSkill) {

        for (String skills : inputSkill) {
            SkillDTO skillDTO = new SkillDTO();

            skillDTO.setUserno(userno);
            skillDTO.setLanguagename(skills);
            skillDTO.setLevel(1);

            Skill skill = modelMapper.map(skillDTO, Skill.class);
            Skill result = skillRepository.save(skill);

            if (result.getLanguagename().isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR");
            }
        }

        return ResponseEntity.ok().body("저장");
    }

    /**
     * 기술스택 삭제
     */
    public ResponseEntity<?> deleteSkill(int sno) {
        if(sno<=0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        else{
            skillRepository.deleteById(sno);
            return ResponseEntity.ok().body(1);
        }

    }

    public ResponseEntity<?> selectJob() {
        List<Job> jobs = jobRepository.findAll();
        log.info("이이잉" + jobs);
        if (jobs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        } else {
            List<JobDTO> result = jobs.stream()
                    .map(job -> modelMapper.map(job, JobDTO.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(result);

        }
    }

    public ResponseEntity<?> selectExp(int userno) {
        List<Experience> experiences = experienceRepository.findAllByUserno(userno);

        if(experiences.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        else {
            List<ExperienceDTO> experienceDTOS = experiences.stream()
                    .map(exp-> modelMapper.map(exp, ExperienceDTO.class))
                    .toList();
            return ResponseEntity.ok().body(experienceDTOS);
        }

    }


    public ResponseEntity<?> addExe(ExperienceDTO experienceDTO) {

    log.info("서비스..1" + experienceDTO);

    int userno = experienceDTO.getUserno();
    List<Experience> experiences = experienceRepository.findAllByUserno(userno);

    // exeno가 있는지 확인하기위해 exeno를 넣을 Map
    Map<String, Integer> experienceMap = new HashMap<>();

    for(Experience experience: experiences){
        log.info("서비스..2" + experience.getExeno());
        experienceMap.put("key",experience.getExeno());

    }

    Integer key = experienceMap.get("key");

    log.info("서비스..2" + key);

    if(key==null){
        // 존재하지 않으므로 추가
        Experience experience = modelMapper.map(experienceDTO, Experience.class);

        log.info("존재하지 않음 " + experience);
        Experience result = experienceRepository.save(experience);

        if(result.getExeno()<=0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        else return ResponseEntity.ok().body(experience);
    }else{
        // 존재하니까 수정
        int result = experienceMapper.updateExp(experienceDTO);

        log.info("존재 " +result);

        if(result<=0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        else return ResponseEntity.ok().body(result);

        }
    }

    @Transactional
    public ResponseEntity<?> deleteAcc (String pass, int userno){
        if(pass.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");

        else{
            Optional<Users> users = userRepository.findById(userno);

            Users users1 = users.get();
            boolean isMatch = passwordEncoder.matches(pass, users1.getPass());
        
            log.info("디코딩"+isMatch);

            if(isMatch){
                LocalDateTime currentDateTime = LocalDateTime.now();

                UsersDTO newUsersDTO = new UsersDTO();
                newUsersDTO.setLeaveDate(currentDateTime);

                int result = usersMapper.insertLeaveDate(userno, currentDateTime);
                log.info("탈퇴완" + result);

                if(result<=0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
                else return ResponseEntity.ok().body(isMatch);

            }else{
                return ResponseEntity.ok().body(isMatch);
            }
        }

    }
}

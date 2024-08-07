package kr.co.fifoBack.mapper;

import kr.co.fifoBack.dto.user.UsersDTO;
import kr.co.fifoBack.service.user.EmailService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface UsersMapper {
    /**중복검사*/
    public int duplicateTest(@Param("param")String param);

    /**정보 수정*/
    public int updateProfile(@Param("userno")int userno, @Param("type")String type, @Param("information") String information);

    /**프로필 업로드*/
    public void uploadProfile(int userno, String thumb);

    /**이메일 찾기*/
    public String findEmail(String name, String hp);

    /**비밀번호 변경*/
    public int changePass(String email, String pass);

    /**이메일로 이름 찾기*/
    public String selectNameForEmail(String email);
}

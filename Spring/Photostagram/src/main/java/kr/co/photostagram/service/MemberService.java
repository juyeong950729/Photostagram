package kr.co.photostagram.service;

import kr.co.photostagram.dao.MemberDAO;
import kr.co.photostagram.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;
import java.util.Random;

/*
    이름 : 김진우
    날짜 : 2023/03/24
    내용 : MemberService
 */

@Slf4j
@Service
public class MemberService {

    @Autowired
    private MemberDAO dao;

    @Autowired
    private PasswordEncoder encoder;

    public int insertMember(MemberVO vo){
        // 패스워드 암호화
        vo.setPassword(encoder.encode(vo.getPassword()));
        return dao.insertMember(vo);
    }
    public int chkUserName(String userName){
        return dao.chkUserName(userName);
    }
    public int chkEmail(String email) {
        return dao.chkEmail(email);
    }

    public String searchId(String name, String email) {
        return dao.searchId(name, email);
    }
    public String searchPass(String userName, String email) {
        return dao.searchPass(userName, email);
    }

    public int changePass(String userName, String pass) {
        pass = encoder.encode(pass);
        return dao.changePass(userName, pass);
    }

}

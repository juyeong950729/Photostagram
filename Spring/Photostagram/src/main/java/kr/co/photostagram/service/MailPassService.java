package kr.co.photostagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailPassService {

    @Autowired
    private JavaMailSender emailSender;
    public String ePw;

    // 6자리 랜덤 코드 생성
    public String createPass() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '!', '@', '#', '$', '%', '^', '&', '*', '(', ')'};

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    // 이메일 양식
    private MimeMessage createPassMessage(String to)throws Exception{
        ePw = createPass();
//        System.out.println("보내는 대상 : "+ to);
//        System.out.println("임시 비밀번호 : "+ePw);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to); // 보내는 대상
        message.setSubject("Photostagram 임시 비밀번호 발송"); // 제목

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> Photostagram </h1>";
        msgg+= "<br>";
        msgg+= "<p>임시 비밀번호를 발송하였습니다.<p>";
        msgg+= "<br>";
        msgg+= "<p>로그인 후 비밀번호를 변경하여주세요.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>임시 비밀번호 입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "임시 비밀번호 : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("wlsdndi2328@gmail.com","photostagram"));//보내는 사람

        return message;
    }

    // 이메일 코드 보내는 메서드
    public String sendPassEmail(String to) throws Exception{
        MimeMessage message = createPassMessage(to);
        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }


}

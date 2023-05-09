package kr.co.photostagram.vo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardVO {

    private int no;
    private String content;
    private int user_no;
    private int like;
    private String rdate;
    private List<MultipartFile> files;



    private String username;

    private String profileImg;
}

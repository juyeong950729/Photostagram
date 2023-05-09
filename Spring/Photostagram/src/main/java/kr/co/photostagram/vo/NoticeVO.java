package kr.co.photostagram.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeVO {
    private int content_no;
    private int user_no;
    private int my_no;
    private String content;
    private String rdate;
    private String username;
    private String profileImg;
    private String type;
    private String thumb;
    private int count;
}

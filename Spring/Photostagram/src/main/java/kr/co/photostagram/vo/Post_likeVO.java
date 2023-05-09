package kr.co.photostagram.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Post_likeVO {
    private int no;
    private int post_no;
    private int user_no;
    private String rdate;
}

package kr.co.photostagram.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Comment_likeVO {
    private int no;
    private int user_no;
    private int comment_no;
    private String rdate;
}

package kr.co.photostagram.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserTagVO {
    private int no;
    private int post_no;
    private int tag_user_no;
    private int top;
    private int left;
    private int page;
    private String username;
}

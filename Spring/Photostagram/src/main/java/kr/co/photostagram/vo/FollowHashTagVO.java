package kr.co.photostagram.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FollowHashTagVO {

    private int no;
    private int tag_no;
    private int user_no;

}

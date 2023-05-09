package kr.co.photostagram.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HashTagVO {
    private int no;
    private String hashtag;

    private int tag_no;
    private int user_no;

    // 해시태그_포스트 개수
    private int countPost;

    // 팔로우 여부
    private int followResult;
}

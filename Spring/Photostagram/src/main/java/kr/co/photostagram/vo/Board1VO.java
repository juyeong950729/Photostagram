package kr.co.photostagram.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board1VO {
    private int hashtag_no;
    private int post_no;

    private String hashtag;
}

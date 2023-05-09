package kr.co.photostagram.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChattingVO {
    private int no;
    private String rdate;
    private String message;
    private int user_no;
    private int room;

    //추가 필드
    private String profileImg;
}

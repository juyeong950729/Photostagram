package kr.co.photostagram.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room_MemberVO {
    private int no;
    private int room;
    private int user;
    private String rdate;
}

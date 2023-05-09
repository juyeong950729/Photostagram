package kr.co.photostagram.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomVO {
    private int no;
    private int me;
    private String rdate;

    private List<Room_MemberVO> room_members;
    private List<MemberVO> members;
}

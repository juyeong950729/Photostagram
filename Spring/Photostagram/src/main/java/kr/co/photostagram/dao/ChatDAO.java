package kr.co.photostagram.dao;

import kr.co.photostagram.DTO.ChatRoom;
import kr.co.photostagram.DTO.MessageDTO;
import kr.co.photostagram.DTO.RoomDTO;
import kr.co.photostagram.vo.ChattingVO;
import kr.co.photostagram.vo.MemberVO;
import kr.co.photostagram.vo.RecommendVO;
import kr.co.photostagram.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChatDAO {

    public List<MemberVO> findAllUsers(String keyword);
    public int insertChatRoom(RoomVO vo);
    public int insertChatRoomMember(@Param("room") int room, @Param("user_no") int user_no);
    public List<RoomVO> selectChatRoomList(int me);
    public List<RoomDTO> selectNowRoom(@Param("room_no") int room_no);
    public int insertMessages(MessageDTO vo);
    public List<ChattingVO> selectMessages(int room);
    public int[] selectChatRoomNotMine(int user);
    public RoomVO selectChatRoomsNotMine(int room_no);
    public List<RecommendVO> selectRecommends(int user_no);
    public List<ChatRoom> selectChatRoomHave(int me);
    public int selectRoomOwner(int room_no);
    public int deleteRoom(@Param("room_no") int room_no, @Param("user_no") int user_no);
    public int deleteRoomMemberOne(@Param("room_no") int room_no, @Param("user_no") int user_no);
    public int deleteRoomMemberAll(int room_no);
    public int deleteChats(int room_no);
}

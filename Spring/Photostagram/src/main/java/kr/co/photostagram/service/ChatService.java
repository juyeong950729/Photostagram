package kr.co.photostagram.service;

import kr.co.photostagram.DTO.ChatRoom;
import kr.co.photostagram.DTO.MessageDTO;
import kr.co.photostagram.DTO.RoomDTO;
import kr.co.photostagram.dao.ChatDAO;
import kr.co.photostagram.vo.ChattingVO;
import kr.co.photostagram.vo.MemberVO;
import kr.co.photostagram.vo.RecommendVO;
import kr.co.photostagram.vo.RoomVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatDAO dao;

    public List<MemberVO> findAllUsers(String keyword){
        return dao.findAllUsers(keyword);
    }

    @Transactional
    public int insertChatRoom(ArrayList<Integer> user_no, int my_no) {
        RoomVO vo = new RoomVO();
        vo.setMe(my_no);
        Arrays.sort(user_no.toArray());

        List<ChatRoom> rooms = dao.selectChatRoomHave(my_no);
        log.info("roomsIhave : " + rooms);
        log.info("roomsIhave size : " + rooms.size());
        boolean isNewRoom = false;
        int no = 0;
        for (int i = 0; i < rooms.size(); i++) {
            List<Integer> users = new ArrayList<>();

            ChatRoom chatRoom = rooms.get(i);

            for(int j=0 ; j<chatRoom.getMes().size() ; j++){
                log.info("i : "+i);
                if (chatRoom.getMes().get(j) == my_no) {
                    // 내가 개설한 방  검사
                    log.info("j  : " + j);
                    users = chatRoom.getUsers();
                    Arrays.sort(users.toArray());
                    log.info("users : " + users);
                    isNewRoom = Arrays.equals(users.toArray(), user_no.toArray());
                    log.info("isNewRoom" + isNewRoom);
                }else{
                    // 다른 사람이 개설한 방에 내가 있을 경우 검사
                    log.info("j2  : " + j);

                    List<Integer> join = new ArrayList<>();
                    join.addAll(chatRoom.getMes());
                    join.addAll(chatRoom.getUsers());
                    join.remove(Integer.valueOf(my_no));

                    Arrays.sort(join.toArray());
                    log.info("users2 : " + users);
                    isNewRoom = Arrays.equals(join.toArray(), user_no.toArray());
                    log.info("isNewRoom2" + isNewRoom);
                }

                if (isNewRoom == true) {
                    log.info("rooms_no : " + chatRoom.getNo());
                    no = chatRoom.getNo();
                    return no;
                }
            }
        }


            dao.insertChatRoom(vo);

            for (int user : user_no) {
                dao.insertChatRoomMember(vo.getNo(), user);
            }

            no = vo.getNo();

            return no;

    }

    public List<RoomVO> selectChatRoomList(int me){
        return dao.selectChatRoomList(me);
    }

    public List<RoomDTO> selectNowRoom(int room_no){
        return dao.selectNowRoom(room_no);
    }

    public int insertMessages(MessageDTO vo){
        return dao.insertMessages(vo);
    }

    public List<ChattingVO> selectMessages(int room){
        return dao.selectMessages(room);
    }

    public List<RoomVO> selectChatRoomNotMine(int user){
        int[] rooms_no = dao.selectChatRoomNotMine(user);
        List<RoomVO> rooms = new ArrayList<>();
        for(int no : rooms_no){
            RoomVO vo = dao.selectChatRoomsNotMine(no);
            rooms.add(vo);
        }
        return rooms;
    }

    public List<RecommendVO> selectRecommends(int user_no){
        return dao.selectRecommends(user_no);
    }

    @Transactional
    public int deleteChat(int room_no, int user_no){
        int owner = dao.selectRoomOwner(room_no);
        int result;
        if(owner == user_no){
            //방 주인이 나가는 경우
            dao.deleteRoomMemberAll(room_no);
            dao.deleteChats(room_no);
            result = dao.deleteRoom(room_no, user_no);
        }else{
            // 방 멤버가 나가는 경우
            result = dao.deleteRoomMemberOne(room_no, user_no);
        }
        return result;
    }
}

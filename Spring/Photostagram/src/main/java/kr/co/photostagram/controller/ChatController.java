package kr.co.photostagram.controller;

import kr.co.photostagram.DTO.RoomDTO;
import kr.co.photostagram.service.ChatService;
import kr.co.photostagram.service.MainService;
import kr.co.photostagram.service.ProfileService;
import kr.co.photostagram.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MainService mainService;

    @Autowired
    private ChatService service;

    @GetMapping(value={"chat", "chat/index"})
    public String chat(Principal principal, Model model){
        MemberVO user =  profileService.selectMember(principal.getName());
        // 검색기록 요청
        List<SearchListVO> searchList = mainService.selectSearchItemRecent(user.getNo());

        log.info("user_no : "+user.getNo());
        log.info("searchList : "+searchList);

        model.addAttribute("user", user);
        model.addAttribute("searchList", searchList);

        // 알림
        List<NoticeVO> notices = mainService.selectNotices(user.getNo());

        log.info("notices : "+notices);

        model.addAttribute("notices", notices);

        // 채팅방 조회
        List<RoomVO> rooms = service.selectChatRoomList(user.getNo());
        log.info("rooms : "+rooms);

        model.addAttribute("rooms", rooms);

        // 다른 사람이 만든 채팅방 조회
        List<RoomVO> rooms2 = service.selectChatRoomNotMine(user.getNo());
        log.info("rooms2 : "+rooms2);

        model.addAttribute("rooms2", rooms2);

        // 추천인 조회
        List<RecommendVO> recommends = service.selectRecommends(user.getNo());
        log.info("recommends : "+recommends);

        model.addAttribute("recommends", recommends);
        return "chat/main";
    }

    @GetMapping("chat/content")
    public String content(Principal principal, Model model, int room_no){
        MemberVO user =  profileService.selectMember(principal.getName());
        // 검색기록 요청
        List<SearchListVO> searchList = mainService.selectSearchItemRecent(user.getNo());

        log.info("user_no : "+user.getNo());
        log.info("searchList : "+searchList);

        model.addAttribute("user", user);
        model.addAttribute("searchList", searchList);

        // 알림
        List<NoticeVO> notices = mainService.selectNotices(user.getNo());

        log.info("notices : "+notices);

        model.addAttribute("notices", notices);

        // 채팅방 조회
        List<RoomVO> rooms = service.selectChatRoomList(user.getNo());
        log.info("rooms : "+rooms);

        model.addAttribute("rooms", rooms);

        // 현재 채팅방 채팅내역 조회
        List<ChattingVO> chats = service.selectMessages(room_no);
        log.info("chats : "+chats);
        //log.info("chat profileImg : "+chats.get(1).getProfileImg());

        model.addAttribute("chats", chats);

        // 다른 사람이 만든 채팅방 조회
        List<RoomVO> rooms2 = service.selectChatRoomNotMine(user.getNo());
        log.info("rooms2 : "+rooms2);

        model.addAttribute("rooms2", rooms2);

        // 추천인 조회
        List<RecommendVO> recommends = service.selectRecommends(user.getNo());
        log.info("recommends : "+recommends);

        model.addAttribute("recommends", recommends);

        // 현재 채팅방 조회
        List<RoomDTO> roomNow = service.selectNowRoom(room_no);
        log.info("roomNow : "+roomNow);

        List<Integer> roomMembers = new ArrayList<>();

        for(RoomDTO now : roomNow){
            roomMembers.add(now.getUser());
        }

        if(roomMembers.contains(user.getNo())){
            model.addAttribute("roomNow", roomNow);
        }else{
            return "redirect:/chat/index";
        }
        return "chat/content";
    }

    @ResponseBody
    @PostMapping("findAllUsers")
    public Map<String, List<MemberVO>> findAllUsers(String keyword){
        List<MemberVO> users = service.findAllUsers(keyword);

        Map<String, List<MemberVO>> resultMap = new HashMap<>();
        resultMap.put("users", users);

        return resultMap;
    }

    @ResponseBody
    @PostMapping("goChattingRoom")
    public Map<String, Integer> goChattingRoom(@RequestParam(value="user_no") ArrayList<Integer> user_no, int my_no){
        log.info("goChattingRoom...0"+user_no);
        log.info("goChattingRoom...1"+my_no);
        int result = service.insertChatRoom(user_no, my_no);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        log.info("goChattingRoom...3...for끝");
        return resultMap;
    }

    @ResponseBody
    @PostMapping("deleteChat")
    public Map<String, Integer> deleteChat(int room_no, int user_no){
        int result = service.deleteChat(room_no, user_no);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        log.info("deleteChat...3...for끝");
        return resultMap;
    }
}

package kr.co.photostagram.controller;

import kr.co.photostagram.entity.UserEntity;
import kr.co.photostagram.security.MyUserDetails;
import kr.co.photostagram.service.QuestService;
import kr.co.photostagram.vo.ImageVO;
import kr.co.photostagram.vo.PostVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class QuestController {
    @Autowired
    private QuestService service;

    @GetMapping("board/quest")
    public String quest(Authentication authentication, Model model){
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        UserEntity user = myUserDetails.getUser();

        List<PostVO> allImg = service.selectAllImg();
        log.info("allImg : " + allImg);

        model.addAttribute("user", user);
        model.addAttribute("allImg", allImg);
        return "board/quest";
    }
}

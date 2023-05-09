package kr.co.photostagram.controller;

import kr.co.photostagram.service.MainService;
import kr.co.photostagram.service.ProfileService;
import kr.co.photostagram.service.SearchService;
import kr.co.photostagram.vo.MemberVO;
import kr.co.photostagram.vo.NoticeVO;
import kr.co.photostagram.vo.PostVO;
import kr.co.photostagram.vo.SearchListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class SearchController {

    @Autowired
    private SearchService service;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MainService mainService;

    @GetMapping(value = {"search", "search/index"})
    public String index(Principal principal, Model model, int no){

        MemberVO user =  profileService.selectMember(principal.getName());

        log.info("user : "+user);

        // 해시태그 인기 게시물 조회
        List<PostVO> postLikeList = service.selectPostsByLike(no);

        // 해시태그 관련 게시물 조회
        List<PostVO> posts = service.selectPostGroupbyHashTag(no);

        // 해시태그 이름 조회
        String hashtagName = service.selectHashTagName(no);

        // 해시태그 게시물 개수 조회
        int countHashTag = service.selectCountHashTag(no);

        // 검색기록 요청
        List<SearchListVO> searchList = mainService.selectSearchItemRecent(user.getNo());

        log.info("user_no : "+user.getNo());
        log.info("searchList : "+searchList);

        model.addAttribute("searchList", searchList);

        // 알림
        List<NoticeVO> notices = mainService.selectNotices(user.getNo());
        log.info("notices : "+notices);

        model.addAttribute("notices", notices);

        log.info("posts_likelist : "+postLikeList);
        log.info("posts_hashtag : "+posts);

        /*** 해시태그 팔로우 여부 확인 ***/
        int result = service.searchHashFollow(no, user.getNo());

        model.addAttribute("result", result);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("countHashTag", countHashTag);
        model.addAttribute("hashtagName", hashtagName);
        model.addAttribute("postLikeList", postLikeList);
        return "search/index";
    }
}

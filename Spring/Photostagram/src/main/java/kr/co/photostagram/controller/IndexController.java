package kr.co.photostagram.controller;

import kr.co.photostagram.service.IndexService;
import kr.co.photostagram.service.MainService;
import kr.co.photostagram.service.ProfileService;
import kr.co.photostagram.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private IndexService service;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MainService mainService;

    @GetMapping(value = {"/", "index"})
    public String index(Model model, Principal principal) throws IndexOutOfBoundsException{

        MemberVO user =  profileService.selectMember(principal.getName());
        // 검색기록 요청
        List<SearchListVO> searchList = mainService.selectSearchItemRecent(user.getNo());
//        log.info("searchList....indexController : "+searchList);

        List<MemberVO> members = service.selectUser();

        List<MemberVO> followings = service.selectFollowing(user.getNo());
        // 팔로잉 되어있는 "유저넘버" 리스트
        List<Integer> following_usersNo = new ArrayList<>();
        following_usersNo.add(user.getNo()); // 로그인 한 유저번호

        // 회원님을 위한 추천 (미 팔로잉자)
        for(int i = 0; i < followings.size(); i++){
            MemberVO index = followings.get(i);
            // 팔로잉 되어있으면 추천리스트에서 삭제
            members.remove(index);

            // usersNo add
            int user_no = followings.get(i).getNo();
            following_usersNo.add(user_no); // 상대방 유저번호
        }
        // 회원님을 위한 추천에 (로그인 한 본인이 뜨지않게)
        for(int j = 0; j < members.size(); j++){
            MemberVO followfilter = members.get(j);
            if(followfilter.getNo() == user.getNo()){
                members.remove(followfilter);
            }
        }

        // 게시글 조회
        List<PostVO> articles = service.selectArticles(following_usersNo);
//        log.info("articles : " + articles);

        // 댓글 조회
        List<CommentVO> comments = service.selectComment();

        model.addAttribute("articles", articles);
        model.addAttribute("members", members);
        model.addAttribute("followings", followings);
        model.addAttribute("user", user);
        model.addAttribute("searchList", searchList);

        // 알림
        List<NoticeVO> notices = mainService.selectNotices(user.getNo());
//        log.info("notices : "+notices);

        model.addAttribute("notices", notices);
        model.addAttribute("comments", comments);
//        log.info("followings" + followings);
        return "index";
    }

    @GetMapping("follow")
    @ResponseBody
    public Map follow(Principal principal, @RequestParam int following){
        MemberVO user =  profileService.selectMember(principal.getName());
        int follower = user.getNo();

        int followResult = 0;
        followResult = profileService.insertFollowing(follower, following);

        Map map = new HashMap();
        map.put("result", followResult);

        return map;
    }

    // 댓글 작성
    @PostMapping("CmtRegister")
    @ResponseBody
    public Map<String, Object> cmtRegister(@RequestBody CommentVO vo){
        service.insertComment(vo);
        Map<String, Object> map = new HashMap<>();

        map.put("result", 1);
        map.put("no",vo.getNo());
        map.put("user_no", vo.getUser_no());

        return map;
    }
    // 답글 작성
    @PostMapping("respCmtRegister")
    @ResponseBody
    public Map<String, Object> respCmtRegister(@RequestBody CommentVO vo){
        service.insertRespComment(vo);
        Map<String, Object> map = new HashMap<>();

        map.put("result", 1);
        map.put("no",vo.getNo());
        map.put("user_no", vo.getUser_no());

        return map;
    }



    // 게시글 좋아요 클릭 시
    @PostMapping("ArticleLikeAdd")
    @ResponseBody
    @Transactional
    public Map ArticleLike(@RequestBody PostVO vo){
        int result = 0;
        result = service.insertArticleLikeAdd(vo);

        log.info(" =============================== ");
        log.info("     좋아요 result : " + result);
        log.info(" =============================== ");

        // Table : `post` , Column : like 업데이트
        if(result > 0){
            service.postLikeAddUpdate(vo);
        }

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

    // 게시글 좋아요 취소 시
    @PostMapping("DeleteArticleLike")
    @ResponseBody
    public Map deleteArticleLike(@RequestBody PostVO vo){
        int result = 0;
        result = service.deleteArticleLike(vo);

        log.info(" =============================== ");
        log.info("     좋아요 취소 : " + result);
        log.info(" =============================== ");
        if(result > 0){
            service.postLikeDelUpdate(vo);
        }

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

    // 댓글 좋아요 클릭 시
    @PostMapping("CommentLikeAdd")
    @ResponseBody
    @Transactional
    public Map CommentLike(@RequestBody Comment_likeVO vo){
        int result = 0;
        result = service.insertCommentLikeAdd(vo);

        log.info(" =============================== ");
        log.info("     댓글 좋아요 : " + result);
        log.info(" =============================== ");

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

    // 댓글 좋아요 취소 시
    @PostMapping("CommentLikeDel")
    @ResponseBody
    @Transactional
    public Map CommentLikeDel(@RequestBody Comment_likeVO vo){
        int result = 0;
        result = service.deleteCommentLike(vo);

        log.info(" =============================== ");
        log.info("     댓글 좋아요취소 : " + result);
        log.info(" =============================== ");

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }
    @GetMapping("deleteComment")
    @ResponseBody
    public Map deleteComment(@RequestParam("comment_no") int comment_no){
        int result = 0;
        result = service.deleteComment(comment_no);

        Map map = new HashMap();
        map.put("result", result);

        log.info("result : " +  result);

        return map;
    }

    @PostMapping("insertBook")
    @ResponseBody
    @Transactional
    public Map insertBook(@RequestBody Post_saveVO vo){
        int result = 0;
        result = service.insertPostSave(vo);

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

    @PostMapping("deleteBook")
    @ResponseBody
    @Transactional
    public Map deleteBook(@RequestBody Post_saveVO vo){
        int result = 0;
        result = service.deleteBookmark(vo);

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

}

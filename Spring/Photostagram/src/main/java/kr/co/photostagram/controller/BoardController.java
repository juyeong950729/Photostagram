package kr.co.photostagram.controller;

import kr.co.photostagram.entity.UserEntity;
import kr.co.photostagram.security.MyUserDetails;
import kr.co.photostagram.service.BoardService;
import kr.co.photostagram.service.IndexService;
import kr.co.photostagram.service.ProfileService;
import kr.co.photostagram.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService service;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private IndexService indexService;

    @GetMapping("board/post")
    public String post(Authentication authentication, Model model, int no) {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        UserEntity user = myUserDetails.getUser();
        log.info("user : " + user);
        /*** 게시물 작성자 ***/
        //BoardVO user = service.selectMember(myName);

        /*** 게시물 내용 ***/
        BoardVO post = service.selectPost(no);
        log.info("post : " + post);


        /*** 게시자 아이디, 프로필 ***/
        /*
        BoardVO user = BoardVO.builder()
                .user_no(post.getUser_no())
                .username(post.getUsername())
                .profileImg(post.getProfileImg())
                .build();
        */

        /*** 해쉬태그 ***/
        List<Board1VO> hashes = service.selectPostHashTag(no);
//       log.info("hashes : " + hashes);

        /*** 게시물 사진 ***/
        List<ImageVO> images = service.selectimages(no);
//        log.info("images : " + images);

        /*** 게시물 작성 날짜 ***/
        PostVO content_like_time = service.selectContentLikeTime(no);

        /*** 게시물 댓글 ***/
        List<CommentVO> articleComments = indexService.selectComment();
        log.info("comment ~~~~ : " + articleComments);

        /*** 댓글 작성 시간 ***/
        List<NoticeVO> noticesTime = service.selectNoticesTime(no);

        /***태그된 사람***/
        List<UserTagVO> userTags = service.selectUserTags(no);
        log.info("userTags :"+userTags);

        Post_likeVO post_like_user = service.selectPostLike(no, user.getNo());
        log.info("post like null Check : " +  post_like_user);

        List<PostVO> plusimg = service.selectPlusImg(no);
//        log.info("plusimg : " + plusimg);

        model.addAttribute("comments", articleComments);
        model.addAttribute("NowArticleNo", no);

        model.addAttribute("user", user);
        model.addAttribute("post", post);
        model.addAttribute("hashes", hashes);
        model.addAttribute("images", images);
        model.addAttribute("noticesTime", noticesTime);
        model.addAttribute("content_like_time", content_like_time);
        model.addAttribute("plusimg", plusimg);
        model.addAttribute("userTags", userTags);
        model.addAttribute("post_like_user", post_like_user);


        return "board/post";
    }

    @ResponseBody
    @PostMapping("BoardComment")
    public Map<String, Object> boardComment(CommentVO vo){

        log.info("comment : " + vo.getComment());
        log.info("user_no : " + vo.getUser_no());
        log.info("post_no : " + vo.getPost_no());

        int result = service.insertComment(vo);

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        map.put("comment", vo.getComment());
        map.put("user_no", vo.getUser_no());
        map.put("post_no", vo.getPost_no());

        return map;
    }

    @ResponseBody
    @PostMapping("BoardRespComment")
    public Map<String, Object> BoardRespComment(@RequestBody CommentVO vo){

        int result = service.insertRespComment(vo);

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        return map;
    }



    @PostMapping("detailPostLike")
    @ResponseBody
    @Transactional
    public Map detailPostLike(@RequestBody PostVO vo){
        int result = 0;
        // post_like : Insert
        result = service.detailPostLikeAdd(vo);

        // Table : `post` , Column : like 업데이트
        if(result > 0){
            service.detailPostLikeUpdate(vo);
        }

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

    // 게시글 좋아요 취소 시
    @PostMapping("detailPostDelLike")
    @ResponseBody
    public Map deleteArticleLike(@RequestBody PostVO vo){
        int result = 0;
        result = service.detailPostDelAdd(vo);

        if(result > 0){
            service.detailPostDelLikeUpdate(vo);
        }

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

    // 댓글 좋아요 클릭 시
    @PostMapping("detailCommentLike")
    @ResponseBody
    @Transactional
    public Map detailCommentLikeAdd(@RequestBody Comment_likeVO vo){
        int result = 0;
        result = service.detailPostCommentLikeAdd(vo);

        log.info(" =============================== ");
        log.info("     댓글 좋아요 : " + result);
        log.info(" =============================== ");

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

    // 댓글 좋아요 취소 시
    @PostMapping("detailCommentLikeDel")
    @ResponseBody
    public Map CommentLikeDel(@RequestBody Comment_likeVO vo){
        int result = 0;
        result = service.detailPostCommentLikeDel(vo);

        log.info(" =============================== ");
        log.info("     댓글 좋아요취소 : " + result);
        log.info(" =============================== ");

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }


    @PostMapping("deletePost")
    @ResponseBody
    public Map<String, Integer> deletePost(int post_no){
        int result = service.deletePost(post_no);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @PostMapping("modifyPost")
    @ResponseBody
    public Map<String, Integer> modifyPost(ModifyPostVO vo){
        int result = service.modifyPost(vo);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }
}
package kr.co.photostagram.controller;

import kr.co.photostagram.service.MyService;
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

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.*;

@Slf4j
@Controller
public class MyController {

    @Autowired
    private MyService service;

    @Autowired
    private ProfileService profileService;

    @GetMapping("my/interaction/like")
    public String like(Principal principal, Model model) {
        MemberVO user = profileService.selectMember(principal.getName());

        int[] postNo = service.selectLikePostNo(user.getNo());
        int[] joinDate = service.selectJoinDate(user.getNo());
        Map<Integer, PostVO> map = new HashMap<>();

        for (int i=0; i<postNo.length; i++){
            PostVO article = service.selectPost(postNo[i]);
            map.put(i, article);
        }

        //Map<Integer, PostVO> sortMap = new TreeMap<>(map);
        Map<Integer, PostVO> sortMap = new TreeMap<>(Comparator.reverseOrder());
        sortMap.putAll(map);

        model.addAttribute("user", user);
        model.addAttribute("sortMap", sortMap);
        model.addAttribute("cate", "interaction");
        model.addAttribute("joinDate", joinDate);
        return "my/interaction/like";
    }

    @GetMapping("my/interaction/comment")
    public String comment(Principal principal, Model model) {

        MemberVO user = profileService.selectMember(principal.getName());
        int[] joinDate = service.selectJoinDate(user.getNo());
        List<PostVO> posts = service.selectMyCommentPosts(user.getNo());

        Map<Integer, PostVO> map = new HashMap<>();
        for (int i=0; i<posts.size(); i++) {
            List<CommentVO> comments = service.selectMyComments(posts.get(i).getNo(), user.getNo());
            posts.get(i).setComments(comments);
            map.put(i, posts.get(i));
        }
        Map<Integer, PostVO> sortMap = new TreeMap<>(Comparator.reverseOrder());
        sortMap.putAll(map);



        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("sortMap", sortMap);
        model.addAttribute("cate", "interaction");
        model.addAttribute("joinDate", joinDate);
        return "my/interaction/comment";
    }

    @GetMapping("my/photos/posts")
    public String posts(Principal principal, Model model) {
        MemberVO user = profileService.selectMember(principal.getName());
        int[] joinDate = service.selectJoinDate(user.getNo());
        List<PostVO> articles = service.selectPosts(user.getNo());

        Map<Integer, PostVO> map = new HashMap<>();

        for (int i=0; i<articles.size(); i++){
            int artNo = articles.get(i).getNo();
            PostVO article = profileService.selectThumb(user.getNo(), artNo);
            map.put(i, article);
        }

        Map<Integer, PostVO> sortMap = new TreeMap<>(map);

        model.addAttribute("user", user);
        model.addAttribute("cate", "photos");
        model.addAttribute("sortMap", sortMap);
        model.addAttribute("joinDate", joinDate);
        return "my/photos/posts";
    }

    @GetMapping("my/history")
    public String history(Principal principal, Model model){

        MemberVO user = profileService.selectMember(principal.getName());
        int[] joinDate = service.selectJoinDate(user.getNo());
        List<HistoryVO> history = service.selectHistory(user.getNo());

        model.addAttribute("user", user);
        model.addAttribute("history", history);
        model.addAttribute("joinDate", joinDate);
        model.addAttribute("cate", "history");
        return "my/history";
    }

    @ResponseBody
    @PostMapping("my/history")
    public Map<Integer, HistoryVO> history (Principal principal, PeriodVO vo,
                                            @RequestParam(value="sortValue") String sortValue) {

        int no = profileService.selectMember(principal.getName()).getNo();
        String[] period = service.sortPeriod(vo);

        Map<Integer, HistoryVO> sortMap = new HashMap<>();

        List<HistoryVO> list = service.sortSelectHistory(no, period[0], period[1], sortValue);
        for (int i=0; i<list.size(); i++){
            sortMap.put(i, list.get(i));
        }
        Map<Integer, HistoryVO> map = new TreeMap<>(sortMap);

        return map;
    }

    @Transactional
    @ResponseBody
    @PostMapping("my/delete")
    public Map<String, Integer> delete(Principal principal,
                   @RequestParam("type") String type, @RequestParam("checkArray") int[] checkArray){

        MemberVO user = profileService.selectMember(principal.getName());
        int no = user.getNo();
        int result = service.deleteCheck(no, type, checkArray);

        Map<String, Integer> map = new HashMap<>();
        map.put("result", result);
        return map;

    }

    @ResponseBody
    @PostMapping("my/sort")
    public Map<Integer, PostVO> sort (Principal principal, PeriodVO vo,
                                      @RequestParam(value="sortValue") String sortValue,
                                      @RequestParam(value="type") String type){

        int no = profileService.selectMember(principal.getName()).getNo();
        String[] period = service.sortPeriod(vo);
        Map<Integer, PostVO> map = service.mySort(no, period[0], period[1], sortValue, type);

        return map;
    }


}

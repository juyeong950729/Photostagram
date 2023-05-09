package kr.co.photostagram.controller;

import kr.co.photostagram.service.MainService;
import kr.co.photostagram.service.ProfileService;
import kr.co.photostagram.vo.HashTagVO;
import kr.co.photostagram.vo.MemberVO;
import kr.co.photostagram.vo.PostVO;
import kr.co.photostagram.vo.SearchListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private MainService service;

    @Autowired
    private ProfileService profileService;

    @ResponseBody
    @PostMapping("postUpload")
    public Map<String, Integer> postUpload(PostVO vo){

        log.info("vo : "+vo);

        int result = service.uploadFiles(vo);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        //log.info("files : "+files.get(0).getOriginalFilename());
        log.info("PostVO content: "+vo.getContent());
        log.info("PostVO userno: "+vo.getUser_no());

        return resultMap;
    }

    @ResponseBody
    @PostMapping("searchHashtag")
    public Map<String, List<HashTagVO>> searchHashtag(SearchListVO vo){
        log.info("vo : "+vo);

        List<HashTagVO> result = service.selectHashTag(vo);
        log.info("result : "+result.size());



        Map<String, List<HashTagVO>> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @ResponseBody
    @PostMapping("searchUser")
    public Map<String, List<MemberVO>> searchUser(SearchListVO vo){
        log.info("vo : "+vo);

        List<MemberVO> result = service.selectUser(vo);
        log.info("result : "+result.size());

        Map<String, List<MemberVO>> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @ResponseBody
    @PostMapping("insertSearchResult")
    public Map<String, Integer> insertSearchResult(SearchListVO vo){
        int result = service.insertSearchResult(vo);

        log.info("insertSearchUser : "+result);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @ResponseBody
    @PostMapping("deleteSearch")
    public Map<String, Integer> deleteSearch(int searchNo){
        int result = service.deleteSearch(searchNo);
        log.info("result : "+result);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("deleteSearchAll")
    public Map<String, Integer> deleteSearchAll(int user_no){
        int result = service.deleteSearchAll(user_no);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("insertFollow")
    public Map<String, Integer> insertFollow(int user_no, int my_no){
        int result = service.insertFollow(my_no, user_no);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("deleteFollow")
    public Map<String, Integer> deleteFollow(int user_no, int my_no){
        int result = service.deleteFollow(my_no, user_no);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("findTagUser")
    public Map<String, List<MemberVO>> findTagUser(String search){
        List<MemberVO> members = service.selectTagUsers(search);
        Map<String, List<MemberVO>> resultMap = new HashMap<>();
        resultMap.put("result", members);
        return resultMap;
    }
}

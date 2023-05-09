package kr.co.photostagram.controller;

import kr.co.photostagram.service.MainService;
import kr.co.photostagram.service.MyService;
import kr.co.photostagram.service.ProfileService;
import kr.co.photostagram.service.SearchService;
import kr.co.photostagram.utils.JSFunction;
import kr.co.photostagram.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.*;

/**
 * 날짜 : 2023/03/08
 * 이름 : 조주영
 * 내용 : profile 컨트롤러
 */

@Slf4j
@Controller
public class ProfileController {

    @Autowired
    private ProfileService service;

    @Autowired
    private MainService mainService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private MyService myService;

    @GetMapping(value = {"profile", "profile/index"})
    public String index(Principal principal, Model model, String username) {

        int searchResult = service.searchUserName(username);

        if (searchResult == 0){
            return "redirect:/index";
        } else {
        
            /*** 사용자, 프로필 페이지 사용자 ***/
            String myName = principal.getName();
            MemberVO member =  service.selectMember(username);
            MemberVO user = service.selectMember(myName);

            int myNo = user.getNo();          // 현재 로그인 된 사용자 번호
            int pageNo = member.getNo();      // 프로필 페이지 사용자 번호

            /*** 사용자 게시물 ***/
            List<PostVO> posts = service.selectPosts(pageNo, 0);      // 게시물 목록
            Map<Integer, PostVO> map = new HashMap<>();                   // 맵 생성

            for (int i=0; i<posts.size(); i++){                           // 게시물 갯수만큼 반복
                int postNo = posts.get(i).getNo();                        // 게시물 번호
                PostVO article = service.selectThumb(pageNo, postNo);     // 게시물 당 첫번째 사진과 사진 갯수 불러오기 (`image` 내에서 같은 `post_no` 중 가장 작은 `no` 값의 `thumb`)
                map.put(i, article);                                      // 인덱스 번호(key) + 게시물 정보(value)로 맵에 전달
            }

            /*** 게시물 최신 순으로 정렬 ***/
            Map<Integer, PostVO> sortMap = new TreeMap<>(map);    // 인덱스 번호(key) 기준으로 정렬

            //Map<Integer, PostVO> sortMap = new TreeMap<>(Comparator.reverseOrder());      ---- 역순정렬 하는 코드
            //sortMap.put(map)                                                              ---- 이후 sortMap에 따로 put 적용해준다.


            /*** 게시물, 팔로워, 팔로잉 ***/
            int post = service.selectCountPost(pageNo);                         // 게시물 갯수
            int myFollower = service.selectCountFollower(pageNo);               // 팔로워 수
            int myFollowing = service.selectCountFollowing(pageNo);             // 팔로잉 하는 수
            int tagFollowing = service.selectCountFollowingTags(pageNo);        // 팔로잉 하는 해시태그 수

            List<MemberVO> myFollowers = service.selectFollowers(pageNo, 0);   // 팔로워 목록
            List<MemberVO> myFollowings = service.selectFollowings(pageNo, 0); // 팔로잉 목록

            /*** 팔로잉 목록 유저들 현재 사용자가 팔로잉 중인지 검색 ***/
            int[] followingArray = new int [myFollowings.size()];
            Map<MemberVO, Integer> followingMap = new HashMap<>();

            for (int i=0; i<myFollowings.size(); i++){
                MemberVO currentUser = service.selectMember(myFollowings.get(i).getUsername());
                followingArray[i] = currentUser.getNo();
                int j = service.searchFollowing(myNo, followingArray[i]);

                followingMap.put(currentUser, j);
                //System.out.println("service1 : "+ j);
                //System.out.println("array1 : "+ followingArray[i]);
            }

            int result = 0;
            Map<MemberVO, Integer> followerMap = new HashMap<>();

            /*** 팔로워 목록 유저들 현재 사용자가 팔로잉 중인지 검색 ***/

            /*** 현재 페이지 사용자와 로그인 사용자가 다른 경우에만 수행 ***/
            if (!username.equals(myName)){
                int[] followerArray = new int[myFollowers.size()];
                for (int i=0; i<myFollowers.size(); i++){
                    MemberVO currentUser = service.selectMember(myFollowers.get(i).getUsername());
                    followerArray[i] = currentUser.getNo();
                    int j = service.searchFollowing(myNo, followerArray[i]);

                    followerMap.put(currentUser, j);
                    //System.out.println("service2 : "+ j);
                }
                System.out.println("array2 : "+ followerArray);

                /*** 팔로워 팔로잉 버튼 ***/
                result = service.searchFollowing(myNo, pageNo);  // 로그인 사용자가 현재 페이지 사용자를 팔로잉 했는지 여부 (0 = 하지않음, 1 = 함)

            /*** 현재 페이지 사용자와 로그인 사용자가 같은 경우 팔로워 목록만 저장 ***/
            } else {
                int[] followerArray = new int[myFollowers.size()];
                for (int i=0; i<myFollowers.size(); i++){
                    MemberVO currentUser = service.selectMember(myFollowers.get(i).getUsername());
                    followerArray[i] = currentUser.getNo();
                    followerMap.put(currentUser, 1);
                }
            }

            // 검색기록 요청
            List<SearchListVO> searchList = mainService.selectSearchItemRecent(user.getNo());

            log.info("user_no : "+user.getNo());

            // 알림
            List<NoticeVO> notices = mainService.selectNotices(user.getNo());

            log.info("notices : "+notices);

            model.addAttribute("notices", notices);
            model.addAttribute("user", user);
            model.addAttribute("member", member);
            model.addAttribute("searchList", searchList);
            model.addAttribute("username", username);
            model.addAttribute("myName", myName);
            model.addAttribute("post", post);
            model.addAttribute("myFollower", myFollower);
            model.addAttribute("myFollowers", myFollowers);
            model.addAttribute("myFollowing", myFollowing);
            model.addAttribute("myFollowings", myFollowings);
            model.addAttribute("tagFollowing", tagFollowing);
            model.addAttribute("result", result);
            model.addAttribute("sortMap", sortMap);
            model.addAttribute("followingMap", followingMap);
            model.addAttribute("followerMap", followerMap);
            return "profile/index";
        }
    }

    /*** 무한스크롤 페이지 게시물 불러오기 ***/
    @ResponseBody
    @PostMapping("profile/post")
    public Map<Integer, PostVO> post (String username, int pg){

        /*** 프로필 페이지 사용자 ***/
        MemberVO member =  service.selectMember(username);
        int pageNo = member.getNo();      // 프로필 페이지 사용자 번호
        pg = 12 * pg;

        /*** 사용자 게시물 ***/
        List<PostVO> posts = service.selectPosts(pageNo, pg);         // 게시물 목록
        Map<Integer, PostVO> map = new HashMap<>();                   // 맵 생성

        for (int i=0; i<posts.size(); i++){                           // 게시물 갯수만큼 반복
            int postNo = posts.get(i).getNo();                        // 게시물 번호
            PostVO article = service.selectThumb(pageNo, postNo);     // 게시물 당 첫번째 사진과 사진 갯수 불러오기 (`image` 내에서 같은 `post_no` 중 가장 작은 `no` 값의 `thumb`)
            map.put(i, article);                                      // 게시물 번호(key) + 게시물 썸네일 (value)로 맵에 전달
        }

        /*** 게시물 최신 순으로 정렬 ***/
        Map<Integer, PostVO> data = new TreeMap<>(map);    // 게시물 번호(key) 기준으로 정렬

        return data;
    }

    /*** 팔로잉 유저 불러오기 ***/
    @ResponseBody
    @PostMapping("profile/followers")
    public Map<Integer, MemberVO> followers (Principal principal, String username, int pg, String type){

        log.info("followers start...");

        /*** 프로필 페이지 사용자 ***/
        MemberVO member =  service.selectMember(username);
        int pageNo = member.getNo();      // 프로필 페이지 사용자 번호
        int myNo = service.selectMember(principal.getName()).getNo();   // 로그인 사용자 번호
        pg = 12 * pg;
        log.info("pageNo : " + pageNo);
        log.info("pg : "+ pg);
        log.info("type : "+ type);

        Map<Integer, MemberVO> map = new HashMap<>();                            // 맵 생성
        List<MemberVO> followList = null;                                        // 리스트 선언

        /*** 팔로우 리스트 ***/
        if ("follower".equals(type)){
            followList = service.selectFollowers(pageNo, pg);             // 팔로워 리스트
            log.info("here1");
        } else {
            followList = service.selectFollowings(pageNo, pg);            // 팔로잉 리스트
            log.info("here2");
        }

        //System.out.println(followList);

        int i = 0;
        for (MemberVO follow : followList){                             // 팔로워 수 만큼 반복
            follow.setFollowResult(service.searchFollowing(myNo, service.selectMember(follow.getUsername()).getNo()));
            map.put(i, follow);
            System.out.println(i);
            System.out.println(follow);
            i++;
        }

        /*** 게시물 최신 순으로 정렬 ***/
        Map<Integer, MemberVO> data = new TreeMap<>(map);    // 게시물 번호(key) 기준으로 정렬

        return data;
    }
    
    /*** 팔로잉 해시태그 불러오기 ***/
    @ResponseBody
    @PostMapping("profile/tags")
    public Map<Integer, HashTagVO> tags (Principal principal, String username, int pg) {


        /*** 프로필 페이지 사용자 ***/
        MemberVO member =  service.selectMember(username);
        int pageNo = member.getNo();      // 프로필 페이지 사용자 번호
        int myNo = service.selectMember(principal.getName()).getNo();
        pg = 12 * pg;
        log.info("pageNo : " + pageNo);

        Map<Integer, HashTagVO> map = new HashMap<>();                             // 맵 생성
        List<HashTagVO> followList = service.selectFollowTags(pageNo, pg);

        int i=0;
        for (HashTagVO follow : followList) {
            if (pageNo == myNo) {
                follow.setFollowResult(1);
            } else {
                follow.setFollowResult(service.searchFollowingTag(pageNo, follow.getNo()));
            }
            map.put(i, follow);
            i++;
        }

        Map<Integer, HashTagVO> data = new TreeMap<>(map);

        return data;
    }

    @GetMapping("profile/modify")
    public String modify(Principal principal, Model model){

        MemberVO user =  service.selectMember(principal.getName());
        log.info("user_no : "+user.getNo());

        model.addAttribute("user", user);
        return "profile/modify";
    }

    @Transactional
    @ResponseBody
    @PostMapping("profile/modify")
    public void modify(HttpServletResponse resp, MemberVO vo, Principal principal){
        //log.info("user_no : "+vo.getNo());

        MemberVO preUser = service.selectMember(principal.getName());

        if (vo.getGender().length() > 2){
            vo.setGender(null);
        }

        if (service.searchUserName(vo.getUsername()) == 0){
            myService.updateHistories(preUser, vo);
            myService.insertDetail(vo.getNo(), "id", vo.getUsername());
            service.updateMember(vo);
        } else {
            if ((principal.getName()).equals(vo.getUsername())){
                myService.updateHistories(preUser, vo);
                service.updateMember(vo);
                JSFunction.alertLocation(resp, "수정이 완료되었습니다.", "/Photostagram/profile/modify");
            } else {
                JSFunction.alertLocation(resp, "중복된 사용자 이름입니다. 다른 이름을 입력해주세요.", "/Photostagram/profile/modify");
            }
        }

        JSFunction.alertLocation(resp, "수정이 완료되었습니다. \\n 아이디를 변경할 경우 재로그인이 필요합니다.", "/Photostagram/member/logout");
    }

    @GetMapping("profile/changePass")
    public String changePass(Principal principal, Model model) {
        MemberVO user = service.selectMember(principal.getName());

        model.addAttribute("user", user);
        return "profile/changePass";
    }

    /*** 비밀번호 변경 ***/
    @ResponseBody
    @PostMapping("profile/changePass")
    public void changePass(HttpServletResponse resp, Principal principal, String prePass, String pass1, String pass2){
        
        //String result = null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String dbPass = service.selectMember(principal.getName()).getPassword();
        int no = service.selectMember(principal.getName()).getNo();
        //System.out.println("prePass : "+ prePass);
        //System.out.println("dbUser Password : "+ dbUser.getPassword());

        /*** 입력한 이전 비밀번호가 맞는지 확인 ***/
        if(encoder.matches(prePass, dbPass)){
            //result = "pwConfirmOk";

            /*** 새 비밀번호가 새 비밀번호 확인과 같은지 확인 ***/
            if (pass1.equals(pass2)){
                String newPass = encoder.encode(pass1);
                int result = service.updatePassword(newPass, no);

                /*** 비밀번호 변경에 성공했을 시 로그아웃 ***/
                if (result > 0){
                    myService.insertType(no, "pass");
                    JSFunction.alertLocation(resp, "비밀번호 변경이 완료되었습니다. 다시 로그인해주세요.", "/Photostagram/member/logout");
                }

            } else {
                System.out.println("no");
            }

        } else {
            //result = "pwConfirmNo";
        }

        //System.out.println(result);
        //System.out.println("pass1 : "+ pass1);
        //System.out.println("pass2 : "+ pass2);
    }

    /*** 이전 비밀번호와 같은지 확인 ***/
    @ResponseBody
    @PostMapping("profile/prePass")
    public Map<String, Integer> prePass(Principal principal, String prePass) {
        Map<String, Integer> resultMap = new HashMap<>();

        int result = 0;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String dbPass = service.selectMember(principal.getName()).getPassword();

        if(encoder.matches(prePass, dbPass)){
            result = 1;

        } else {
           result = 0;
        }

        resultMap.put("result", result);
        return resultMap;
    }

    /*** 프로필 사진 업로드 없이 삭제 ***/
    @ResponseBody
    @GetMapping("profile/upload")
    public Map<String, Integer> upload(Principal principal, String type){
        Map<String, Integer> resultMap = new HashMap<>();
        String userName = principal.getName();
        int no = service.selectMember(userName).getNo();
        String photo = service.selectMember(userName).getProfileImg();

        int result = service.uploadProfilePhoto(type, null, no);
        service.deleteProfilePhotoFile(photo);

        resultMap.put("result", result);
        return resultMap;
    }

    /*** 프로필 사진 업로드 ***/
    @ResponseBody
    @PostMapping("profile/upload")
    public Map<String, Integer> upload(@RequestParam("file") MultipartFile file, Principal principal){

        MemberVO member = service.selectMember(principal.getName());
        int no = member.getNo();

        /*** 이전 사진이 있는 경우 삭제 ***/
        if (member.getProfileImg() != null){
            service.deleteProfilePhotoFile(member.getProfileImg());
        }

        /*** 사진 업로드 ***/
        int result = service.uploadProfilePhoto("insert", file, no);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    /*** 팔로우, 언팔로우 요청 처리 ***/
    @GetMapping("profile/follow")
    public String follow(String type, String userName, String myName){

        int following = service.selectMember(userName).getNo();
        int follower = service.selectMember(myName).getNo();

        if (("insert").equals(type)) {
            service.insertFollowing(follower, following);
        } else {
            service.deleteFollowing(follower, following);
        }

        return "redirect:/profile?username="+userName;
    }

    /*** 팔로우, 언팔로우 요청 처리 -- ajax ***/
    @ResponseBody
    @PostMapping("profile/follow")
    public Map<String, Integer> follow(Principal principal, @RequestParam("type") String type, @RequestParam("userName") String userName){

        System.out.println("userName : "+userName);
        System.out.println("type : "+type);


        String myName = principal.getName();
        int following = service.selectMember(userName).getNo();
        int follower = service.selectMember(myName).getNo();
        int result = 0;

        if (("insert").equals(type)){
            result = service.insertFollowing(follower, following);
        } else if (("delete").equals(type)) {
            result = service.deleteFollowing(follower, following);
        } else if (("myDelete").equals(type)){
            result = service.deleteFollowing(following, follower);
        }

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        System.out.println("result : "+ result);

        return resultMap;
    }

    @ResponseBody
    @PostMapping("profile/tagFollow")
    public Map<String, Integer> tagFollow (Principal principal, @RequestParam("no") int no, @RequestParam("type") String type){
        int userNo = service.selectMember(principal.getName()).getNo();
        int result = 0;

        log.info("no : "+ no);
        log.info("userNo : "+ userNo);
        log.info("type : "+ type);

        if(("insert").equals(type)){
            log.info("here1");
            result = service.insertTagFollow(no, userNo);
        } else if (("delete").equals(type)) {
            log.info("here2");
            result = service.deleteTagFollow(no, userNo);
        }

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        log.info("result : "+ result);

        return resultMap;
    }

    @GetMapping("profile/deleteUser")
    public String deleteUser (Principal principal, Model model){
        MemberVO user = service.selectMember(principal.getName());

        model.addAttribute("user", user);
        return "profile/deleteUser";
    }

    /*** 회원 탈퇴 ***/
    @Transactional
    @ResponseBody
    @PostMapping("profile/deleteUser")
    public void deleteUser (HttpServletResponse resp, Principal principal, int no, String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = service.selectMember(principal.getName()).getPassword();

        if(encoder.matches(password, pass)){
            service.deleteMemberData(no);
            service.deleteMember(no);
            JSFunction.alertLocation(resp, "성공적으로 탈퇴되었습니다.", "/Photostagram/member/logout");
        } else {
            JSFunction.alertBack(resp, "비밀번호를 다시 확인해주세요.");
        }

    }

    /*** 해시태그 팔로우 요청 처리 ***/
    @ResponseBody
    @PostMapping("profile/followHashTag")
    public Map<String, Integer> followHashTag (Principal principal, @RequestParam("type") String type, @RequestParam("tagNo") int tagNo) {
        int userNo = service.selectMember(principal.getName()).getNo();
        int result = 0;

        if ("insert".equals(type)){
            result = searchService.insertHashFollow(tagNo, userNo);
        } else {
            result = searchService.deleteHashFollow(tagNo, userNo);
        }

        Map<String, Integer> map = new HashMap<>();
        map.put("result", result);
        return map;
    }

    /*** 태그됨 게시물 ***/
    @ResponseBody
    @PostMapping ("profile/tagged")
    public Map<Integer, PostVO> tagged (String username, int pg){

        MemberVO member = service.selectMember(username);
        int pageNo = member.getNo();
        if (pg == 0){
            pg = 0;
        } else {
            pg = 12 * pg;
        }

        int[] postNo = service.selectTaggedPostsNo(pageNo, pg);
        Map<Integer, PostVO> map = new HashMap<>();
        for (int i=0; i<postNo.length; i++){
            PostVO article = service.selectTaggedPosts(postNo[i]);
            System.out.println(article);
            map.put(i, article);
        }

        Map<Integer, PostVO> data = new TreeMap<>(map);
        System.out.println(data);

        return data;
    }


}

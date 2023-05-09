package kr.co.photostagram.controller;

import kr.co.photostagram.service.MailPassService;
import kr.co.photostagram.service.MailService;
import kr.co.photostagram.service.MemberService;
import kr.co.photostagram.service.MyService;
import kr.co.photostagram.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/*
    이름 : 김진우
    날짜 : 2023/03/24
    내용 : MemberController
 */

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    public MemberService service;

    @Autowired
    public MailService mailService;

    @Autowired
    public MailPassService mailPassService;

    @Autowired
    public MyService myService;

    /**
     * 로그인 페이지
     * @param success
     * @param m
     * @return success 111, 200
     */
    @GetMapping("/login")
    public String login(String success, Model m){
        m.addAttribute("success", success);

        // 로그인 후 뒤로가기 방지
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) { // 로그인이 되어있지 않다면
            return "member/login";
        }else{  // 로그인 했다면
            return "redirect:/index";
        }
    }

    /**
     * 아이디 존재 여부 확인
     * @param userName
     * @return 존재 여부 결과값 0, 1
     */
    @ResponseBody
    @PostMapping("/chkUserName")
    public Map<String, Integer> chkUserName(String userName){
        log.info("chkUsername...");

        int result = service.chkUserName(userName);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    /**
     * 이메일 존재 여부 확인
     * @param email
     * @return 존재 여부 결과값 0, 1
     */
    @ResponseBody
    @PostMapping("/chkEmail")
    public Map<String, Integer> chkEmail(String email) {
        log.info("chkEmail...");

        int result = service.chkEmail(email);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    /**
     * 이메일 코드 보내기
     * @param email
     * @return 이메일 발송 결과 result 0, 1
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/sendEmail")
    public Map<String, Integer> sendEmail(@RequestParam String email) throws Exception{
        log.info("Email Auth...");
        int result = 0;

        int confirm = mailService.sendEmail(email);
        if(confirm != 0){
            result = 1;
        }

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        resultMap.put("confirm", confirm);

        return resultMap;
    }

    /**
     * 유효성 검사 후 회원 등록하기
     * @param vo
     * @param error
     * @return 회원등록 결과값 0, 1
     */
    @ResponseBody
    @PostMapping("/terms")
    public Map<String, Integer> terms(@Valid @RequestBody MemberVO vo, BindingResult error){
        log.info("TermsController...");

        int result = 0;

        if(!error.hasErrors()){ // false면 에러가 없다.
            log.info("No errors");
            result = service.insertMember(vo);
            int userNo = myService.selectUserRecent();
            myService.insertType(userNo, "join");

        }else{
            log.info("errors occurred");
        }

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 아이디 찾기
     * @param name
     * @param email
     * @return 아이디
     */
    @ResponseBody
    @PostMapping("/searchId")
    public Map searchId(@Param("name") String name, @Param("email") String email) {
        log.info("searchId...");

        String userName = service.searchId(name, email);

        Map resultMap = new HashMap<>();
        resultMap.put("userName", userName);
        return resultMap;
    }

    /**
     * 비밀번호 찾기
     * @param userName
     * @param email
     * @return
     */
    @ResponseBody
    @PostMapping("/searchPass")
    public Map searchPass(@Param("userName") String userName, @Param("email") String email) {
        log.info("searchPass...");

        String name = service.searchPass(userName, email);

        Map resultMap = new HashMap<>();
        resultMap.put("name", name);
        return resultMap;
    }

    /**
     * 임시 비밀번호 이메일로 보내기, db값 변경하기
     * @param email
     * @return 보낸 여부 결과값 0, 1
     */
    @Transactional
    @ResponseBody
    @PostMapping("/sendEmailPass")
    public Map<String, Integer> sendEmailPass( @RequestParam String userName, @RequestParam String email) throws Exception {
        log.info("sendEmailPass...");

        String pass = mailPassService.sendPassEmail(email);
        int result = service.changePass(userName, pass);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    @GetMapping("/register")
    public String register(){
        // 로그인 후 뒤로가기 방지
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) { // 로그인이 되어있지 않다면
            return "member/registerLayout";
        }else{  // 로그인 했다면
            return "redirect:/index";
        }
    }

    @GetMapping("/registerForm")
    public String registerForm(){
        //return "member/register";
        return "member/register";
    }

    @GetMapping("/birth")
    public String birth(){
        return "member/birth";
    }

    @GetMapping("/email")
    public String email(){
        return "member/email";
    }

    @GetMapping("/terms")
    public String terms(){
        return "member/terms";
    }

    @GetMapping("/checkId")
    public String checkId() {

        // 로그인 후 뒤로가기 방지
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) { // 로그인이 되어있지 않다면
            return "member/checkIdLayout";
        }else{  // 로그인 했다면
            return "redirect:/index";
        }
    }

    @GetMapping("/checkIdForm")
    public String checkIdForm() {
        return "member/checkId";
    }

    @GetMapping("/checkPass")
    public String checkPass() {
        // 로그인 후 뒤로가기 방지
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) { // 로그인이 되어있지 않다면
            return "member/checkPassLayout";
        }else{  // 로그인 했다면
            return "redirect:/index";
        }
    }

    @GetMapping("/checkPassForm")
    public String checkPassForm() {
        return "member/checkPass";
    }

    @GetMapping("/resultId")
    public String resultId(){
        return "member/resultId";
    }

    @GetMapping("/resultPass")
    public String resultPass(){
        return "member/resultPass";
    }

}

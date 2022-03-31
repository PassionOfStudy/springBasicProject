package com.hanghae99.board.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae99.board.dto.SignupRequestDto;
import com.hanghae99.board.security.UserDetailsImpl;
import com.hanghae99.board.service.KakaoUserService;
import com.hanghae99.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService) {
        this.userService = userService;
        this.kakaoUserService = kakaoUserService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    // 로그인 실패시 동작
    @GetMapping("/user/login/error")
    public String loginError(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails ) {
        if(userDetails == null){
            model.addAttribute("user","anonymous");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("loginError", true);
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/api/signup")
    public @ResponseBody
    String registerUser(@Validated @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        //비밀번호 확인
        int checkPassword = userService.checkPassword(requestDto);
        //아이디 중복체크  위에 만들어서 필요없지만 테스트할때 쓰려고 가입단에서 다시한번 체크
        int checkName = userService.nameCheck(requestDto);
        //비밀번호안에 아이디가 있는지 확인
        int checkNamePassword = userService.namePasswordCheck(requestDto);
        //아래 과정을 모두 통과하면 회원가입 성공!
        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldError().getDefaultMessage();
        } else if (checkNamePassword == 0) {
            System.out.println("비밀번호에 아이디를 포함할 수 없습니다.");
            return "비밀번호에 아이디를 포함할 수 없습니다.";
        } else if (checkPassword == 3) {
            return "비밀번호가 일치하지 않습니다.";
        } else if (checkName == 1) {
            return "중복된 아이디 입니다.";
        } else {
            userService.registerUser(requestDto);
            return "회원가입을 축하합니다!";
        }
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }



}
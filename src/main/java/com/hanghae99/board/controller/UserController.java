package com.hanghae99.board.controller;

import com.hanghae99.board.dto.SignupRequestDto;
import com.hanghae99.board.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
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


}
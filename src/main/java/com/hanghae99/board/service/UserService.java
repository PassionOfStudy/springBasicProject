package com.hanghae99.board.service;

import com.hanghae99.board.dto.SignupRequestDto;
import com.hanghae99.board.model.User;
import com.hanghae99.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 중복체크
    public int nameCheck(SignupRequestDto requestDto) {

        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
        if(found.isPresent()) {
            return 1;
        } else {
            return 0;
        }
    }

    // 회원가입 패스워드 일치 확인
    public int checkPassword(SignupRequestDto requestDto) {
        System.out.println("checkPassword");
        String password = requestDto.getPassword();
        String checkPassword = requestDto.getPasswordCheck();
        if(Objects.equals(password, checkPassword)) {
            return 2;
        } else {
            return 3;
        }
    }

    // 비밀번호에 아이디가 포함되어있는지 확인
    public int namePasswordCheck(SignupRequestDto requestDto) {
        String name = requestDto.getUsername();
        System.out.println(requestDto.getPassword());
        String password = requestDto.getPassword();
        if(password.contains(name)) {
            return 0;
        } else {
            return 1;
        }
    }

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String passwordInput = requestDto.getPassword();
        // 패스워드 암호화
        String password = passwordEncoder.encode(passwordInput);
        User user = new User(username, password);

        userRepository.save(user);
    }
}

package com.hanghae99.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.board.model.User;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET 요청 확인 테스트")
    public void 테스트_GET() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("name", "칩");
        info.add("id", "chip");

        mockMvc.perform(get("/")       // 1, 2
                        .params(info))              // 3
                .andExpect(status().isOk())     // 4
//                .andExpect(content().string("칩의 블로그입니다. chip"))
                .andDo(print());                // 5
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 완료 테스트")
    public void 테스트_POST() throws Exception {

        String content = objectMapper.writeValueAsString(new User("test", "1234"));

        mockMvc.perform(post("/api/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("회원 가입을 축하합니다!"))
                .andDo(print());
    }
}

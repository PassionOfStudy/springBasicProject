package com.hanghae99.board.dto;

import com.hanghae99.board.model.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardRequestDto {
    private String title;
    private String contents;
    private User user;
}

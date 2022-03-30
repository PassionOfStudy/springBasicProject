package com.hanghae99.board.dto;

import com.hanghae99.board.model.Board;
import com.hanghae99.board.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private String text;
    private User user;
    private Board board;
}

package com.hanghae99.board.domain;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String author;
    private String contents;
}

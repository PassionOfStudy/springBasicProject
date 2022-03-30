package com.hanghae99.board.model;

import com.hanghae99.board.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@NoArgsConstructor // 기본생성자를 대신 생성해줍니다.
@Getter
@Setter
@Entity // 테이블임을 나타냅니다.
public class Board extends Timestamped{
    @Id // ID 값, Primary Key로 사용하겠다는 뜻입니다.
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동 증가 명령입니다.
    private Long id;

    @Column(nullable = false) // 컬럼 값이고 반드시 값이 존재해야 함을 나타냅니다.
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

//    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
//    List<Comment> comment = new ArrayList<>();

//    public Board(String title, String contents) {
//        this.title = title;
//        this.contents = contents;
//    }

    public Board(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = requestDto.getUser();
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = requestDto.getUser();
    }
}
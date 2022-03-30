package com.hanghae99.board.controller;

import com.hanghae99.board.model.Board;
import com.hanghae99.board.model.Comment;
import com.hanghae99.board.repository.BoardRepository;
import com.hanghae99.board.dto.BoardRequestDto;
import com.hanghae99.board.repository.CommentRepository;
import com.hanghae99.board.repository.UserRepository;
import com.hanghae99.board.security.UserDetailsImpl;
import com.hanghae99.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@RequiredArgsConstructor
//@RestController
@Controller
public class BoardController {

//    private final BoardService boardService;
//    private final BoardRepository boardRepository;
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;


    //게시글 작성 페이지
    @ResponseBody
    @GetMapping("/api/boards")
    public List<Board> getNotice(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        return boardList;
    }

//    // 게시글 작성 페이지
//    @GetMapping("/api/boards")
//    public @ResponseBody String getNotice(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        Board board = new Board(requestDto);
//        if(userDetails == null){
//            board.getUser().setUsername("임시사용자");
//        }else{
//            board.getUser().setUsername(userDetails.getUser().getUsername());
//        }
//        return "board";
//    }

    // 게시글 작성
    @PostMapping("/api/boards")
    public String createNotice(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardRequestDto requestDto){
        requestDto.setUser(userDetails.getUser());
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return "redirect:/";
    }

    //게시글 한개 조회페이지
    @GetMapping("/api/boards/{id}")
    public String getOneBoard(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(id);
        System.out.println(userDetails.getUser().getUsername());
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        List<Comment> comment = commentRepository.findByBoardIdOrderByModifiedAtDesc(id);
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("editcomment",new Comment());
        model.addAttribute("postcomment",new Comment());
        model.addAttribute("comment", comment);
        model.addAttribute("board",board);
//        comment.get(0).getUser().getUsername()
        return "detailboard";
    }


    @GetMapping("/api/boards/{id}/edit")
    public String getEditBoard(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("board",board);
        return "editboard";
    }


    @PutMapping("/api/boards/{id}/edit")
    public String updateBoard(@PathVariable Long id, @ModelAttribute BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        requestDto.setUser(userDetails.getUser());
        boardService.update(id, requestDto);
        return "redirect:/";

    }

    @DeleteMapping("/api/boards/{id}")
    public String deleteBoard(@PathVariable Long id){
        boardRepository.deleteById(id);
        return "redirect:/";
    }



//    @PostMapping("/api/boards")
//    public Board createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        Board board = new Board(requestDto);
//        return boardRepository.save(board);
//    }
//
//    @GetMapping("/api/boards")
//    public List<Board> getBoards() {
//        return boardRepository.findAllByOrderByModifiedAtDesc();
//    }
//
//
//    @PutMapping("/api/boards/{id}")
//    public Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
//        boardService.update(id, requestDto);
//        return id;
//    }
//
//    @DeleteMapping("/api/boards/{id}")
//    public Long deleteBoard(@PathVariable Long id) {
//        boardRepository.deleteById(id);
//        return id;
//    }
//
//
//
//    @PostMapping("/api/detail")
//    public Board createRegister(@RequestBody Long id) {
//        System.out.println(id);
//        Board detailBoard = boardRepository.findById(id).orElseThrow(
//                () -> new NullPointerException("아이디가 존재하지 않습니다")
//        );
//        return detailBoard;
//    }

}
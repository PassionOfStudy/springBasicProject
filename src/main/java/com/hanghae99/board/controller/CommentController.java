package com.hanghae99.board.controller;

import com.hanghae99.board.dto.CommentRequestDto;
import com.hanghae99.board.model.Board;
import com.hanghae99.board.model.Comment;
import com.hanghae99.board.repository.BoardRepository;
import com.hanghae99.board.repository.CommentRepository;
import com.hanghae99.board.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @PostMapping("/api/boards/{id}/comment")
    public String createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @ModelAttribute CommentRequestDto requestDto){
        Comment comment = new Comment(requestDto);
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(userDetails == null) {
            return "redirect:/user/login";
        }
        comment.setUser(userDetails.getUser());
        comment.setBoard(board);
        commentRepository.save(comment);
    return "redirect:/api/boards/{id}";
    }

    @PutMapping("/api/boards/{id}/comment/{commentId}")
    public String editComment(@PathVariable Long commentId, @ModelAttribute CommentRequestDto requestDto){
        System.out.println(requestDto.getText());
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        comment.setText(requestDto.getText());

        commentRepository.save(comment);
        return "redirect:/api/boards/{id}";
    }

    @DeleteMapping("/api/boards/{id}/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId){
        commentRepository.deleteById(commentId);
        return "redirect:/api/boards/{id}";
    }
}

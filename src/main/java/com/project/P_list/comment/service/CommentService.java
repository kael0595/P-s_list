package com.project.P_list.comment.service;

import com.project.P_list.board.entity.Board;
import com.project.P_list.board.repository.BoardRepository;
import com.project.P_list.comment.dto.CommentDto;
import com.project.P_list.comment.entity.Comment;
import com.project.P_list.comment.repository.CommentRepository;
import com.project.P_list.member.entity.Member;
import com.project.P_list.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    public void create(CommentDto commentDto, String username) {

        System.out.println("boardId : " + commentDto.getBoardId());

        Board board = boardRepository.findById(commentDto.getBoardId()).orElseThrow();

        Member member = memberRepository.findByUsername(username).orElseThrow();

        Comment comment = Comment.builder()
                .board(board)
                .author(member)
                .content(commentDto.getContent())
                .build();
        commentRepository.save(comment);
    }

    public void deleteComment(Long id, String username) {

        Comment comment = commentRepository.findById(id).orElseThrow();

        Member member = memberRepository.findByUsername(username).orElseThrow();

        if (!comment.getAuthor().getUsername().equals(member.getUsername())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    public void updateComment(Long id, String username, CommentDto commentDto) {

        Comment comment = commentRepository.findById(id).orElseThrow();

        Member member = memberRepository.findByUsername(username).orElseThrow();

        if (!comment.getAuthor().getUsername().equals(member.getUsername())) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        comment.setContent(commentDto.getContent());
        comment.setUpdateDt(LocalDateTime.now());
        commentRepository.save(comment);

    }
}

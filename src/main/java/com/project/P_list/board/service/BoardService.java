package com.project.P_list.board.service;

import com.project.P_list.board.dto.BoardDto;
import com.project.P_list.board.entity.Board;
import com.project.P_list.board.repository.BoardRepository;
import com.project.P_list.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board createBoard(BoardDto boardDto, Member member) {
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .author(member)
                .build();
        return boardRepository.save(board);
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

    public void increaseHit(Board board) {
        board.setHit(board.getHit() + 1);
        boardRepository.save(board);
    }
}

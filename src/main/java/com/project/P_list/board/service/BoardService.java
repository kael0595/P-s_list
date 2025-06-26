package com.project.P_list.board.service;

import com.project.P_list.board.dto.BoardDto;
import com.project.P_list.board.entity.Board;
import com.project.P_list.board.repository.BoardRepository;
import com.project.P_list.member.entity.Member;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private Specification<Board> search(String kw) {
        return new Specification<>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Board, Member> u1 = b.join("author", JoinType.LEFT);
                return cb.or(cb.like(b.get("title"), "%" + kw + "%"), // 제목
                        cb.like(b.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"));    // 게시글 작성자
            }
        };
    }

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

    public void updateBoard(Board board, BoardDto boardDto) {
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setUpdateDt(LocalDateTime.now());
        boardRepository.save(board);
    }

    public void deleteBoard(Board board) {
        board.setDeleteYn("Y");
        board.setUpdateDt(LocalDateTime.now());
        boardRepository.save(board);
    }

    public Page<Board> getList(int page, int size, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDt"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));
        Specification<Board> spec = search(kw);
        return boardRepository.findAll(spec, pageable);
    }

    public int countByKeyword(String kw) {
        return boardRepository.countByKeyword(kw);
    }
}

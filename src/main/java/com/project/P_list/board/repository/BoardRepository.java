package com.project.P_list.board.repository;

import com.project.P_list.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAll(Specification<Board> spec, Pageable pageable);

    @Query("SELECT COUNT(b) FROM Board b WHERE b.title LIKE %:kw% OR b.content LIKE %:kw% OR b.author.username LIKE %:kw%")
    int countByKeyword(@Param("kw") String kw);
}

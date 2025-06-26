package com.project.P_list.comment.entity;

import com.project.P_list.board.entity.Board;
import com.project.P_list.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Comment {

    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    private Member author;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
}

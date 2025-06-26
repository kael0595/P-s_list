package com.project.P_list.board.entity;

import com.project.P_list.base.entity.BaseEntity;
import com.project.P_list.comment.entity.Comment;
import com.project.P_list.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Board extends BaseEntity {

    @NotNull
    @Column(nullable = false, length = 100)
    private String title;

    @NotNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Column(nullable = false, length = 1)
    @Builder.Default
    private String deleteYn = "N";

    @NotNull
    @Column(nullable = false)
    @Builder.Default
    private int hit = 0;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    private Member author;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;
}

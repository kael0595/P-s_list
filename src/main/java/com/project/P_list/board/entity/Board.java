package com.project.P_list.board.entity;

import com.project.P_list.base.entity.BaseEntity;
import com.project.P_list.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    @Builder.Default
    private String deleteYn = "N";

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    private Member author;
}

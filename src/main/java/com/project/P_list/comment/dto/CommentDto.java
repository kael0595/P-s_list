package com.project.P_list.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {

    @NotNull
    private Long boardId;

    @NotNull
    private String content;

}

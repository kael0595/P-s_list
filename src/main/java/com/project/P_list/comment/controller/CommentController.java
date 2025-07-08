package com.project.P_list.comment.controller;

import com.project.P_list.base.security.SecurityUser;
import com.project.P_list.comment.dto.CommentDto;
import com.project.P_list.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CommentDto commentDto,
                                    @AuthenticationPrincipal SecurityUser securityUser) {

        commentService.create(commentDto, securityUser.getUsername());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id,
                                           @AuthenticationPrincipal SecurityUser securityUser) {

        commentService.deleteComment(id, securityUser.getUsername());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Long id,
                                           @AuthenticationPrincipal SecurityUser securityUser,
                                           @RequestBody CommentDto commentDto) {

        commentService.updateComment(id, securityUser.getUsername(), commentDto);

        return ResponseEntity.ok().build();

    }

}

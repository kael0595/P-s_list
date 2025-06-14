package com.project.P_list.board.controller;

import com.project.P_list.board.entity.Board;
import com.project.P_list.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(Model model) {

        List<Board> boardList = boardService.findAll();

        model.addAttribute("boardList", boardList);

        return "/board/list";
    }
}

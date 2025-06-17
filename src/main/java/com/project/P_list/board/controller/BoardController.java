package com.project.P_list.board.controller;

import com.project.P_list.base.security.SecurityUser;
import com.project.P_list.board.dto.BoardDto;
import com.project.P_list.board.entity.Board;
import com.project.P_list.board.service.BoardService;
import com.project.P_list.member.entity.Member;
import com.project.P_list.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model) {

        List<Board> boardList = boardService.findAll();

        model.addAttribute("boardList", boardList);

        return "/board/list";
    }

    @GetMapping("/create")
    public String createBoard() {

        return "/board/create";

    }

    @PostMapping("/create")
    public String createBoard(@Valid BoardDto boardDto,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal SecurityUser securityUser) {

        if (bindingResult.hasErrors()) {
            return "board/create";
        }

        Member loginMember = memberService.getMember(securityUser.getUsername());

        Board board = boardService.createBoard(boardDto, loginMember);

        return "redirect:/board/detail/" + board.getId();
    }
}

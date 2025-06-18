package com.project.P_list.board.controller;

import com.project.P_list.base.security.SecurityUser;
import com.project.P_list.board.dto.BoardDto;
import com.project.P_list.board.entity.Board;
import com.project.P_list.board.service.BoardService;
import com.project.P_list.member.entity.Member;
import com.project.P_list.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    private final MemberService memberService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "kw", required = false, defaultValue = "") String kw,
                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                       Model model) {

        Page<Board> paging = boardService.getList(page, kw);

        model.addAttribute("page", page);

        model.addAttribute("kw", kw);

        model.addAttribute("boardList", paging);

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

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id,
                         Model model) {

        Board board = boardService.getBoard(id);

        boardService.increaseHit(board);

        model.addAttribute("board", board);

        return "/board/detail";
    }

    @GetMapping("/detail/{id}/update")
    public String updateBoard(@PathVariable("id") Long id,
                              Model model) {

        Board board = boardService.getBoard(id);

        model.addAttribute("board", board);

        return "/board/update";
    }

    @PostMapping("/detail/{id}/update")
    public String updateBoard(@PathVariable("id") Long id,
                              @Valid BoardDto boardDto,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal SecurityUser securityUser) {

        if (bindingResult.hasErrors()) {
            return "board/update";
        }

        Board board = boardService.getBoard(id);

        Member loginMember = memberService.getMember(securityUser.getUsername());

        if (!board.getAuthor().getUsername().equals(loginMember.getUsername())) {
            return "board/update";
        }

        boardService.updateBoard(board, boardDto);

        return "redirect:/board/detail/" + board.getId();
    }

    @GetMapping("/detail/{id}/delete")
    public String deleteBoard(@PathVariable("id") Long id) {

        Board board = boardService.getBoard(id);

        boardService.deleteBoard(board);

        return "redirect:/board/list";
    }
}

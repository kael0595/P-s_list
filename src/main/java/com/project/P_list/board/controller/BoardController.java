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

import java.nio.file.AccessDeniedException;

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
                       @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                       Model model) {

        Page<Board> paging = boardService.getList(page, size, kw);

        int totalCount = boardService.countByKeyword(kw);

        int totalPages = (int) Math.ceil((double) totalCount / size);

        if (page < 0) page = 0;

        if (page >= totalPages) page = totalPages > 0 ? totalPages - 1 : 0;

        model.addAttribute("paging", paging);

        model.addAttribute("page", page);

        model.addAttribute("kw", kw);

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

    @GetMapping("/update/{id}")
    public String updateBoard(@PathVariable("id") Long id,
                              Model model) {

        Board board = boardService.getBoard(id);

        model.addAttribute("board", board);

        return "/board/update";
    }

    @PostMapping("/update/{id}")
    public String updateBoard(@PathVariable("id") Long id,
                              @Valid BoardDto boardDto,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal SecurityUser securityUser) throws Exception {

        if (bindingResult.hasErrors()) {
            return "board/update";
        }

        Board board = boardService.getBoard(id);

        Member loginMember = memberService.findByUsername(securityUser.getUsername()).orElseThrow(() -> new IllegalArgumentException("접근 권한이 없습니다."));

        if (!board.getAuthor().getUsername().equals(loginMember.getUsername())) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        boardService.updateBoard(board, boardDto);

        return "redirect:/board/detail/" + board.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteBoard(@PathVariable("id") Long id,
                              @AuthenticationPrincipal SecurityUser securityUser) throws Exception {

        Board board = boardService.getBoard(id);

        Member loginMember = memberService.findByUsername(securityUser.getUsername()).orElseThrow(() -> new IllegalArgumentException("접근 권한이 없습니다."));

        if (!board.getAuthor().getUsername().equals(loginMember.getUsername())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        boardService.deleteBoard(board);

        return "redirect:/board/list";
    }
}

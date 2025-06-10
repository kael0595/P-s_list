package com.project.P_list.member.controller;

import com.project.P_list.member.dto.MemberDto;
import com.project.P_list.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm() {
        return "/member/join";
    }

    @PostMapping("/join")
    public String join(@Valid MemberDto memberDto,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member/join";
        }

        if (!memberDto.getPassword().equals(memberDto.getPasswordCnf())) {
            return "member/join";
        }

        memberService.join(memberDto);

        return "/member/login";

    }

    @GetMapping("/login")
    public String loginForm() {
        return "/member/login";
    }
}

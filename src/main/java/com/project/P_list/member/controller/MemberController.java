package com.project.P_list.member.controller;

import com.project.P_list.member.dto.MemberDto;
import com.project.P_list.member.entity.Member;
import com.project.P_list.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
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

    @GetMapping("/mypage/{username}")
    public String mypage(@PathVariable("username") String username,
                         Model model) {

        Member member = memberService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        log.info("member : {}", member);

        model.addAttribute("member", member);

        return "/member/mypage";
    }

    @GetMapping("/mypage/{username}/update")
    public String updateMember(@PathVariable("username") String username,
                               Model model) {

        Member member = memberService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        model.addAttribute("member", member);

        return "/member/update";
    }

    @PostMapping("/mypage/{username}/update")
    public String updateMember(@PathVariable("username") String username,
                               @Valid MemberDto memberDto,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        Member member = memberService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        memberService.updateMember(member, memberDto);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
    }
}

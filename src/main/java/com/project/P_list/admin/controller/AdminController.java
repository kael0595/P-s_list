package com.project.P_list.admin.controller;

import com.project.P_list.base.security.SecurityUser;
import com.project.P_list.member.dto.MemberDto;
import com.project.P_list.member.entity.Member;
import com.project.P_list.member.enums.Grade;
import com.project.P_list.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/members")
    public String memberList(Model model) {
        
        List<Member> memberList = memberService.getAll();

        model.addAttribute("memberList", memberList);

        return "admin/memberList";
    }

    @PostMapping("/updateAuth")
    @ResponseBody
    public ResponseEntity<?> updateAuth(@AuthenticationPrincipal SecurityUser securityUser,
                                        @RequestBody MemberDto memberDto) {

        Member loginMember = memberService.getMember(securityUser.getUsername());

        Member targetMember = memberService.getMember(memberDto.getUsername());

        if (loginMember.getGrade() != Grade.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 권한이 없습니다.");
        }

        memberService.changAuth(targetMember);

        return ResponseEntity.ok().build();
    }
}

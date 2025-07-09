package com.project.P_list.admin.controller;

import com.project.P_list.member.entity.Member;
import com.project.P_list.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

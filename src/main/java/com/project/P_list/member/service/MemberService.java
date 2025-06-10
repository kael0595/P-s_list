package com.project.P_list.member.service;

import com.project.P_list.member.dto.MemberDto;
import com.project.P_list.member.entity.Member;
import com.project.P_list.member.enums.Grade;
import com.project.P_list.member.repository.MemberRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public void join(MemberDto memberDto) {

        Grade grade = determineGrade(memberDto.getUsername());

        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .email(memberDto.getEmail())
                .nickname(memberDto.getNickname())
                .addr1(memberDto.getAddr1())
                .addr2(memberDto.getAddr2())
                .grade(grade)
                .build();

        memberRepository.save(member);

    }

    private Grade determineGrade(String username) {
        return username.startsWith("admin") ? Grade.ADMIN : Grade.USER;
    }
}

package com.project.P_list.member.service;

import com.project.P_list.member.dto.MemberDto;
import com.project.P_list.member.entity.Member;
import com.project.P_list.member.enums.Grade;
import com.project.P_list.member.repository.MemberRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
                .name(memberDto.getName())
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

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public void updateMember(Member member, MemberDto memberDto) {
        if (!member.getPassword().equals(memberDto.getPassword())) {
            member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        }
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setNickname(memberDto.getNickname());
        member.setAddr1(memberDto.getAddr1());
        member.setAddr2(memberDto.getAddr2());
        member.setUpdateDt(LocalDateTime.now());

        memberRepository.save(member);
    }

    public void deleteMember(Member member) {
        member.setDeleteYn("Y");
        member.setUpdateDt(LocalDateTime.now());
        memberRepository.save(member);
    }

    public Member getMember(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없스니다."));
    }
}

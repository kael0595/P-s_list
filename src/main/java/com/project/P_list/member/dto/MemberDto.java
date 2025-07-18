package com.project.P_list.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberDto {

    @NotBlank
    private final String username;

    private final String password;

    private final String passwordCnf;

    @NotBlank
    private final String name;

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String nickname;

    @NotBlank
    private final String addr1;

    private final String addr2;

}

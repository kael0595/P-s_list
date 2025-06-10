package com.project.P_list.member.entity;

import com.project.P_list.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Member extends BaseEntity {

    @NotNull
    @Column(nullable = false)
    @Size(max = 20)
    private String username;

    @NotNull
    @Column(nullable = false)
    @Size(min = 8)
    private String password;

    @NotNull
    @Column(nullable = false)
    @Email
    private String email;

    @NotNull
    @Column(nullable = false)
    private String nickname;

    @NotNull
    @Column(nullable = false)
    private String addr1;

    private String addr2;

    @NotNull
    @Builder.Default
    private String deleteYn = "N";
}

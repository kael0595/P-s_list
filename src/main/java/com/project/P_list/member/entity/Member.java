package com.project.P_list.member.entity;

import com.project.P_list.base.entity.BaseEntity;
import jakarta.persistence.Entity;
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

    private String username;

    private String password;

    private String email;

    private String nickname;

    private String addr1;

    private String addr2;
}

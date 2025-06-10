package com.project.P_list.member.enums;

public enum Grade {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String grade;

    Grade(String grade) {
        this.grade = grade;
    }
}

package com.susol.susolstudy.model.entity;

import lombok.Getter;

@Getter
public enum PostType {
    NOTICE("공지"), GENERAL("일반"), QUESTION("질문");

    private final String koreanName;

    PostType(String koreanName) {
        this.koreanName = koreanName;
    }
}

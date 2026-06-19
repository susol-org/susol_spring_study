package com.susol.susolstudy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false)
    private String userEmailId;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private int userAge;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender userGender;

    private String userShortBio;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Permission userPermission;
}

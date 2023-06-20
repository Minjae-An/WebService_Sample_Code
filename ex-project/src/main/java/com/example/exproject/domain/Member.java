package com.example.exproject.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String memberCustomId;
    private String password;
    private String username;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<Order> orders = new ArrayList<>();

    public Member(String memberCustomId, String password, String username) {
        this.memberCustomId = memberCustomId;
        this.password = password;
        this.username = username;
    }
}

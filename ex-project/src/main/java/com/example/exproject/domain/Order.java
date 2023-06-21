package com.example.exproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Order(Member member) {
        if (member != null) {
            changeMember(member);
        }
    }

    //편의 메서드
    public void changeMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
}

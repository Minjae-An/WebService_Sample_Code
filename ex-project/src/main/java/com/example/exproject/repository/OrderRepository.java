package com.example.exproject.repository;

import com.example.exproject.domain.Member;
import com.example.exproject.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMember(Member member);
}

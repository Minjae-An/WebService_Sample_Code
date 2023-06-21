package com.example.exproject.repository;

import com.example.exproject.domain.Member;
import com.example.exproject.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class OrderRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    public String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            //Convert the byte to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Test
    public void testOrder() {
        String memberCustomId = "exampleId";
        String password = "examplePw";

        /**
         * Spring Security 같은 경우를 활용하면 사용자의 ID, PW 등을
         * 해시함수를 활용해 변환하여 DB에 저장하는 경우가 많다.
         */
        String hashedMemberCustomId = hashSHA256(memberCustomId);
        String hashedPw = hashSHA256(password);
        Member member = new Member(hashedMemberCustomId, hashedPw, "exName");
        memberRepository.save(member);

        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            orderList.add(new Order(member));

        for (Order order : orderList) {
            orderRepository.save(order);
        }

        List<Order> orderListByMember = orderRepository.findByMember(member);
        assertThat(orderList.equals(orderListByMember));
    }


}
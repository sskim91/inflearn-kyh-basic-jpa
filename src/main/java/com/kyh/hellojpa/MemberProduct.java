package com.kyh.hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by sskim on 2021/04/25
 * Github : http://github.com/sskim91
 */
@Entity
public class MemberProduct {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;


    private int count;
    private int price;

    private LocalDateTime orderDateTime;
}

package com.kyh.hellojpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by sskim on 2021/07/18
 * Github : http://github.com/sskim91
 */
@Entity
@Getter
@Setter
public class Child {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;
}

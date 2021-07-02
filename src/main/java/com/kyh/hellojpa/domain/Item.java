package com.kyh.hellojpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by sskim on 2021/06/27
 * Github : http://github.com/sskim91
 */
@Getter
@Setter
@Entity
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;
}

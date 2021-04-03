package com.kyh.hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by sskim on 2021/04/03
 * Github : http://github.com/sskim91
 */
@Entity
public class Member {

    @Id
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

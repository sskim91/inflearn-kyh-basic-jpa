package com.kyh.hellojpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by sskim on 2021/06/27
 * Github : http://github.com/sskim91
 */
@Entity
@Getter
@Setter
public class Movie extends Item{

    private String director;
    private String actor;
}

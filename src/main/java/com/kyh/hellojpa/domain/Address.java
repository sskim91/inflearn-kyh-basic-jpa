package com.kyh.hellojpa.domain;

import lombok.*;

import javax.persistence.Embeddable;

/**
 * Created by sskim on 2021/07/18
 * Github : http://github.com/sskim91
 */
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Address {

    private String city;
    private String street;
    private String zipcode;

}

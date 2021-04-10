package com.kyh.hellojpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by sskim on 2021/04/03
 * Github : http://github.com/sskim91
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne  //멤버 입장에서는 N 팀 입장에서는 1
    @JoinColumn(name = "TEAM_ID")
    private Team team;
}

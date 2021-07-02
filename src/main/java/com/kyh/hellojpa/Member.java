package com.kyh.hellojpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sskim on 2021/04/03
 * Github : http://github.com/sskim91
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Member extends BaseEntity {
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

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    //연관관계 편의 메서드 생성
    //JPA 상태를 변경하는 것은 setter 를 잘 안씀. 로직이 들어가면 새로 만듬.
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}

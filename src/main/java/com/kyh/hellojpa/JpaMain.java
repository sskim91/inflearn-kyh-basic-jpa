package com.kyh.hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by sskim on 2021/04/03
 * Github : http://github.com/sskim91
 */
public class JpaMain {

    public static void main(String[] args) {
        System.out.println("JpaMain.main");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team); //**
            em.persist(member);

            //연관관계 주인이 아닌쪽에서도 사용 가능
            //본인이 정하기 나름. 주인인 쪽에서 하는 게 편해보인다.
//            team.addMember(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());  //flush와 clear 없으면 1차 캐시
            List<Member> members = findMember.getTeam().getMembers();

            System.out.println("===============");
            members.forEach(System.out::println);
            System.out.println("===============");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }
}

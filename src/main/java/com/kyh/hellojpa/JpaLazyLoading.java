package com.kyh.hellojpa;

import com.kyh.hellojpa.domain.Member;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by sskim on 2021/07/18
 * Github : http://github.com/sskim91
 */
public class JpaLazyLoading {
    public static void main(String[] args) {
        System.out.println("JpaMain.main");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);
            em.flush();
            em.clear();

//            Lazy 로딩 테스트
//            example01(em, member1);

//            example02(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.clear();
        }
        emf.close();
    }

    private static void example02(EntityManager em) {
        //즉시로딩은 JPQL에서 N+1 문제를 일으킨다.
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        //SQL: select * from Member //쿼리호출하고 팀도 즉시로딩이라 한번 더 호출한다.
        //SQL: select * from Team where TEAM_ID = ?
    }

    private static void example01(EntityManager em, Member member1) {
        Member findMember = em.find(Member.class, member1.getId());

        System.out.println("findMember = " + findMember.getTeam().getClass());

        System.out.println("===============");
        findMember.getTeam().getName(); //초기화
        System.out.println("===============");
    }
}

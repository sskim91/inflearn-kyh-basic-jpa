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
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("Hello");
//            em.persist(member);

            //수정
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");

            //JPA는 대상이 테이블이 아니라 객체가 대상이된다.
            //JPQL이란 객체를 대상으로 하는 객체지향 쿼리라고 보면된다.
            List<Member> result = em.createQuery("select m from Member  as m", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member = " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }
}

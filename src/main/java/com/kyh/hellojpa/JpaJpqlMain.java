package com.kyh.hellojpa;

import com.kyh.hellojpa.domain.Member;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sskim on 2021/07/18
 * Github : http://github.com/sskim91
 */
public class JpaJpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

//            example01(em);
//            example02(em);
            example03(em);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.clear();
        }
        emf.close();
    }

    private static void example03(EntityManager em) {
        //파라미터 바인딩
        TypedQuery<Member> query = em.createQuery("select m from Member as m where m.id = :id", Member.class);
        query.setParameter("id", 10);
        List<Member> resultList = query.getResultList();
    }

    private static void example02(EntityManager em) {
        TypedQuery<Member> query = em.createQuery("select m from Member as m where m.id = 10", Member.class);

        //결과가 하나 이상일 때, 리스트 반환
        //없으면 빈 값 리턴
        List<Member> resultList = query.getResultList();

        //결과가 정확히 1개, 단일 객체 반환
        //없으면 Exception 이기 때문에 조심해야된다. 없으면 NoResultException, 둘 이상이면 NonUniqueResultException
        Member singleResult = query.getSingleResult();
    }

    private static void example01(EntityManager em) {
        List<Member> result = em.createQuery("select m from Member m where m.username like '%kim%'",
                Member.class)
                .getResultList();

        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }
}

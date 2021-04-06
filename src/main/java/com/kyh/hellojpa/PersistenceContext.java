package com.kyh.hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by sskim on 2021/04/04
 * Github : http://github.com/sskim91
 */
public class PersistenceContext {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            insert1Cache(em);
//            select1Cache(em);
//            createEntityLazyQuery(em);
//            updateJPAdirtyChecking(em);
//            flushJPA(em);
//            detachJPA(em);

            //Commit 하는 시점에 영속성에 들어가있는 객체가 쿼리로 넘어감
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }

    private static void detachJPA(EntityManager em) {
        Member findMember = em.find(Member.class, 5L);
        findMember.setName("UPDATE");   //변경감지

        em.detach(findMember);  ///준영속 상태로 만듬
        //업데이트 쿼리가 날라가지 않는다.
    }

    private static void flushJPA(EntityManager em) {
//        Member member200 = new Member(201L, "member201");
//        em.persist(member200);

        em.flush(); //쓰기 지연 sql에 올리는거임 작업의 단위 마지막은 commit 이다!
        System.out.println("=======================");
    }

    private static void updateJPAdirtyChecking(EntityManager em) {

        Member findMember = em.find(Member.class, 5L);
        findMember.setName("UPDATE");   //변경감지
        //JPA는 엔티티를 영속성 컨텍스트에 보관할 때, 최초 상태를 복사해서 저장해 두는데 이걸 스냅샷이라고함.
        //그리고 플러시 시점에 스냅샷과 엔티티를 비교해서 변경된 엔티티를 찾아서 update 한다.
    }

    private static void createEntityLazyQuery(EntityManager em) {
//        Member member1 = new Member(5L, "A");
//        Member member2 = new Member(6L, "B");

//        em.persist(member1);
//        em.persist(member2);
        //바로 데이터베이스에 저장하는 것이 아니라 쓰기 지연 sql 저장소에 모아놨다가 commit 하는 순간에 저장
        System.out.println("============");
    }

    private static void select1Cache(EntityManager em) {
        Member findMember1 = em.find(Member.class, 1L);
        Member findMember2 = em.find(Member.class, 1L);

        //영속 엔티티의 동일성 보장
        System.out.println("영속 엔티티의 동일성 보장 = " + (findMember1 == findMember2));
    }

    public static void insert1Cache(EntityManager em) {
        //비영속 상태
        Member member = new Member();
        member.setId(1L);
        member.setName("Hello JPA");

        //영속상태
        System.out.println("=== BEFORE ===");
        em.persist(member);
        //준영속상태
        //em.detach(member);
        System.out.println("=== AFTER ===");

//        Member findMember = em.find(Member.class, 3L);

//        System.out.println("findMember.getId() = " + findMember.getId());
//        System.out.println("findMember.getName() = " + findMember.getName());

    }
}

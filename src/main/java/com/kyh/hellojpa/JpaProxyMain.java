package com.kyh.hellojpa;

import com.kyh.hellojpa.domain.Member;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author sskim
 */
public class JpaProxyMain {
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

            Member member2 = new Member();
            member2.setUsername("member1");
            em.persist(member2);

            em.flush();
            em.clear();

//            example01(em, member1);

            //프록시 특징

            //프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것이 아님, 초기화 되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
            //example02(em, member1);

            //프록시 객체는 원본 엔티티를 상속받음, 따라서 타입 체크시 == 대신 instance of 사용
            //example03(em, member1, member2);

            //영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
            //example04(em, member1);

            //심화
            //example05(em, member1);

            //영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때 프록시를 초기화하면 문제가 발생
            //example06(em, member1);

            //프록시 유틸리티 메소드
            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());
//            refMember.getUsername();
            //강제 초기화 간지나게
            Hibernate.initialize(refMember);
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.clear();
        }
        emf.close();
    }

    private static void example06(EntityManager em, Member member1) {
        Member refMember = em.getReference(Member.class, member1.getId());
        System.out.println("refMember = " + refMember.getClass());

        //em.detach(refMember);
        //em.close();
        em.clear();

        //org.hibernate.LazyInitializationException: could not initialize proxy
        refMember.getUsername();   //준영속 상태일 때 호출하면 에러
    }

    private static void example05(EntityManager em, Member member1) {
        Member refMember = em.getReference(Member.class, member1.getId());
        System.out.println("refMember = " + refMember.getClass());
        refMember.getUsername();   //강제로 호출 프록시 초기화가 되었음.

        Member findMember = em.find(Member.class, member1.getId());
        //find로 썻지만 했지만 프록시 객체가 나온다.
        //JPA에서는 ==이 참으로 보장해주야 하기 때문
        System.out.println("findMember = " + findMember.getClass());
    }

    private static void example04(EntityManager em, Member member1) {
        Member m1 = em.find(Member.class, member1.getId());
        System.out.println("m1 = " + m1.getClass());

        Member reference = em.getReference(Member.class, member1.getId());
        System.out.println("reference = " + reference.getClass());  //레퍼런스도 프록시 아니라 클래스다.

        //JPA에서는 같은 트랜잭션레벨(같은 영속성) 안에서 항상 같은 ==이 나와야한다.
        //== 비교할 때를 true로 만들어줘야 한다. 보장해줘야한다.
        System.out.println("a == a: " + (m1 == reference));

    }

    private static void example03(EntityManager em, Member member1, Member member2) {
        Member m1 = em.find(Member.class, member1.getId());
//            Member m2 = em.find(Member.class, member2.getId());
        Member m2 = em.getReference(Member.class, member2.getId());
        System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));
        System.out.println("m1 == m2: " + (m1 instanceof Member));
    }

    private static void example02(EntityManager em, Member member) {
        //before와 after가 같다.
        //before findMember = class com.kyh.hellojpa.domain.Member$HibernateProxy$26sjs36H
        //after findMember = class com.kyh.hellojpa.domain.Member$HibernateProxy$26sjs36H
        Member findMember = em.getReference(Member.class, member.getId());
        System.out.println("before findMember = " + findMember.getClass());
        System.out.println("findMember.getUsername() = " + findMember.getUsername());
        System.out.println("after findMember = " + findMember.getClass());
    }

    private static void example01(EntityManager em, Member member) {
        //프록시 객체 조회 em.getReference()
        Member findMember = em.getReference(Member.class, member.getId());
        //프록시 객체가 나옴. ex) com.kyh.hellojpa.domain.Member$HibernateProxy$X2LUR69y
        System.out.println("findMember = " + findMember.getClass());
        //id 에서는 레퍼런스를 찾을 때 값이 있기 때문에 그냥 가져올 수 있음
        System.out.println("findMember.getId() = " + findMember.getId());
        //DB에 있는거를 가져와야 하기때문에 호출하는 시점에 쿼리 나감.
        System.out.println("findMember.getUsername() = " + findMember.getUsername());
    }
}

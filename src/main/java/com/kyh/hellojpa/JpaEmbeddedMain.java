package com.kyh.hellojpa;

import com.kyh.hellojpa.domain.Address;
import com.kyh.hellojpa.domain.Member;
import com.kyh.hellojpa.domain.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by sskim on 2021/07/18
 * Github : http://github.com/sskim91
 */
public class JpaEmbeddedMain {
    public static void main(String[] args) {
        System.out.println("JpaMain.main");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Address address = new Address("city", "street", "zipcode");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            member.setWorkPeriod(new Period());

            Member member2 = new Member();
            member2.setUsername("member1");
            member2.setHomeAddress(address);
            member2.setWorkPeriod(new Period());

            //member 에서만 도시를 바꾼다고 생각하지만 실제로는 둘 다 바뀜
            //임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험하다.
            //값 타입은 불변 객체로 만들어야된다.
            //생성자로만 만들고 비즈니스 로직 단계에서는 setter를 없앰
//            member.getHomeAddress().setCity("new City");

            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.clear();
        }
        emf.close();
    }
}

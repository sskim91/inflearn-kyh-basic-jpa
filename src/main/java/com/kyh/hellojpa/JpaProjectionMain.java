package com.kyh.hellojpa;

import com.kyh.hellojpa.domain.Member;
import com.kyh.hellojpa.domain.MemberDTO;
import com.kyh.hellojpa.domain.Team;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sskim on 2021/07/26
 * Github : http://github.com/sskim91
 */
public class JpaProjectionMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            em.flush();
            em.clear();

//            example01(em);
//            example02(em);

            List<MemberDTO> resultList = em.createQuery("select new com.kyh.hellojpa.domain.MemberDTO(m.username, m.age) from Member as m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = resultList.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.clear();
        }
        emf.close();
    }

    private static void example01(EntityManager em) {
        //영속성 컨텍스트에 존재하나?
        List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
                .getResultList();
        //존재한다. 엔티티 프로젝션을 하면 리스트에 나와있는 모든 것들이 영속성 컨텍스트에 다 걸린다.
        //그래서 아래와 같이 하면 수정 쿼리가 나간다.
        Member findMember = resultList.get(0);
        findMember.setUsername("update");
    }

    private static void example02(EntityManager em) {
        //조인을 명시적으로 하는게 좋다.
        List<Team> teamList = em.createQuery("select t from Member as m join m.team t", Team.class)
                .getResultList();
    }
}

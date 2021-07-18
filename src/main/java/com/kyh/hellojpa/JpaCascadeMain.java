package com.kyh.hellojpa;

import com.kyh.hellojpa.domain.Child;
import com.kyh.hellojpa.domain.Member;
import com.kyh.hellojpa.domain.Parent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by sskim on 2021/07/18
 * Github : http://github.com/sskim91
 */
public class JpaCascadeMain {
    public static void main(String[] args) {
        System.out.println("JpaMain.main");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();

            em.persist(parent);
//            CASCADE 옵션 all로 parent만 persist해도 나머지 child도 알아서 persist 된다.
//            em.persist(child1);
//            em.persist(child2);


            //orphanRemoval을 true 로 설정하면 컬렉션에서 빠진 애는 자동으로 삭제 쿼리가 나간다.
            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);

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

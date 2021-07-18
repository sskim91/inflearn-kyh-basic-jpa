package com.kyh.hellojpa;

import com.kyh.hellojpa.domain.Address;
import com.kyh.hellojpa.domain.AddressEntity;
import com.kyh.hellojpa.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

/**
 * Created by sskim on 2021/07/18
 * Github : http://github.com/sskim91
 */
public class JpaValueTypeCollectionMain {
    public static void main(String[] args) {
        System.out.println("JpaMain.main");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("HOME CITY", "STREET", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("OLD1", "STREET", "10000"));
            member.getAddressHistory().add(new AddressEntity("OLD2", "STREET", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("==============START==============");
            Member findMember = em.find(Member.class, member.getId());

            //값 타입 컬렉션은 기본적으로 LAZY 지연로딩이다.
            List<AddressEntity> addressHistory = findMember.getAddressHistory();
            for (AddressEntity address : addressHistory) {
                System.out.println("address.getCity() = " + address.getAddress().getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            //수정을 해보려면 homeCity ->newCity
            //값 타입은 참조타입이라 무조건 새로 갈아 끼워야 한다.
            Address homeAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", homeAddress.getStreet(), homeAddress.getZipcode()));

            //값 타입 컬렉션 업데이트해보자
            //치킨 -> 한식으로 바꾸고싶다.
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            //주소를 바꿔보자
            //대부분의 컬렉션 remove는 equals로 한다. 그래서 equals와 hash를 필수로 재정의해야함.
//            findMember.getAddressHistory().remove(new Address("OLD1", "STREET", "10000"));
//            findMember.getAddressHistory().add(new Address("newCity", "STREET", "10000"));

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

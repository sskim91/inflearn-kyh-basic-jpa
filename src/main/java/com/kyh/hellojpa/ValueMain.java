package com.kyh.hellojpa;

import com.kyh.hellojpa.domain.Address;

/**
 * Created by sskim on 2021/07/18
 * Github : http://github.com/sskim91
 */
public class ValueMain {

    public static void main(String[] args) {

        //기본값 (primitive type)은 값이 복사만 된다.
//        int a = 10;
//        int b = a;
//        a = 20;
//
//        System.out.println("a = " + a);
//        System.out.println("b = " + b);


        //integer 같은 래퍼클래스나 String 같은 특수한 클래스는 공유 가능한 객체이지만 변경 x
        //변경 자체를 불가능하게 해서 사이드 이펙트 자체를 발생 안하게함
//        Integer a = new Integer(10);
//        Integer b = a;
//
//        System.out.println("a = " + a);
//        System.out.println("b = " + b);

        int a = 10;
        int b = 10;
        System.out.println("a == b: "+ (a == b));

        Address address1 = new Address("city", "street", "zipcode");
        Address address2 = new Address("city", "street", "zipcode");

        System.out.println("address1 == address2: "+ (address1 == address2));

        //Object의 equals는 == (동일성) 비교를 하기때문에 equals를 재정의해줘야한다.
        System.out.println("address1 equals address2: "+ (address1.equals(address2)));

    }
}

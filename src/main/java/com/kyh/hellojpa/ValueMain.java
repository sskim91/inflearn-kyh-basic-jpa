package com.kyh.hellojpa;

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

    }
}

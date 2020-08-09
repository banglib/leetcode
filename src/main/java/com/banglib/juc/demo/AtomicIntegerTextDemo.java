package com.banglib.juc.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author banglib
 * @date 2020/8/8 22:53
 */
public class AtomicIntegerTextDemo {


    private AtomicInteger count = new AtomicInteger();


    public void add(int addNum){
        count.addAndGet(addNum);
    }


    public void decr(){
        count.decrementAndGet();
    }






}

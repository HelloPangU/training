package io.kimmking.HomeWork05;

import java.lang.reflect.Method;

public class AOPClass {
    public void start(Method method) {
        System.out.println("aop开始Method start。。。。。");
    }

    public void end(Method method) {
        System.out.println(" aop结束Method end.....");
    }
}

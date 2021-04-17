package io.kimmking.HomeWork05;

public class MainClass {
    public static void main(String[] args) {
        IHello hello = (IHello)new DProxy().bind(new Hello(),new AOPClass());
        hello.sayHello("pangU");
        hello.sayGoodBye("pangU");
    }
}

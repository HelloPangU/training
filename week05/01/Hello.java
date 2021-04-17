package io.kimmking.HomeWork05;

public class Hello implements IHello{
    @Override
    public void sayHello(String name) {
        System.out.println("hello " + name);
    }

    @Override
    public void sayGoodBye(String name) {
        System.out.println(name + " bye~");
    }
}

package com.jvm;

import lombok.SneakyThrows;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public class HelloClassLoader extends  ClassLoader{

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        new HelloClassLoader().findClass("Hello").newInstance();
    }

    @SneakyThrows
    @Override
    protected Class<?> findClass(String name){
        String filePath = "E:\\source\\springBoot\\demo\\src\\main\\java\\com\\jvm\\Hello.xlass";
        FileInputStream fin = new FileInputStream(filePath);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        while ((len = fin.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        byte[] decodes = out.toByteArray();
        int length = decodes.length;
        for(int i=0;i<length;i++){
            decodes[i] =(byte) (~decodes[i]);
        }

        return defineClass(name,decodes,0,length);
    }

}

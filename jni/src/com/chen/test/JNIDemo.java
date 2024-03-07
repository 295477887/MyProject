package com.chen.test;

public class JNIDemo {

    public native void testHelloWorld();

    public static void main(String[] args){
        System.loadLibrary("helloJNI");
        JNIDemo jniDemo = new JNIDemo();
        jniDemo.testHelloWorld();
    }
}

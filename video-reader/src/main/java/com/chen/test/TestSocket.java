//package com.chen.test;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.Socket;
//
///**
// * @author: ChenJie
// * @date 2019/4/2
// */
//public class TestSocket {
//    public static void main(String[] args) {
//        try {
//            Socket socket = new Socket("172.16.1.86",1935);
//            OutputStream os = socket.getOutputStream();
//            os.write("c0".getBytes());
//            os.flush();
//
//            InputStream is = socket.getInputStream();
//            byte[] box = new byte[10];
//            int len = 0;
//            while(-1!=(len = is.read(box))) {
//                String msg = new String(box, 0, len);
//                System.out.println(len+"=="+msg);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

package com.chen.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author: ChenJie
 * @date 2019/4/4
 */
public class PipeTest {
    public static void main(String[] args) {

        String inputFile = "F:\\study\\rtmp\\windows\\orange.mp4";
//
        String outputFile = "rtmp://10.30.50.195:1935/myapp/stream6";


//        PipedOutputStream pos = new PipedOutputStream();
//        PipedInputStream pis = new PipedInputStream();
//        try {
//            pis.connect(pos);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        new MyProducer(pos,inputFile).start();
//        new MyConsumer(pis,outputFile).start();


        /**
         * 流程
         * 1 建立输入输出流
         * 2 绑定输入输出流
         * 3 向缓冲区写数据
         * 4 读取缓冲区数据
         */
//        Producer producer = new Producer();
//        Consumer consumer = new Consumer();
//        PipedInputStream in = consumer.getIn();
//        PipedOutputStream out = producer.getOut();
//
//        try {
//            out.connect(in);
//            producer.start();
//            consumer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        Consumer consumer = new Consumer();
        PipedInputStream in = consumer.getIn();
        PipedOutputStream out = new PipedOutputStream();
        try {
            out.connect(in);
            consumer.start();


            FileInputStream fis = null;
            fis = new FileInputStream("F:\\study\\rtmp\\windows\\264\\song.264");
            byte [] buf = new byte[10240];
            int len;
            int i = 0;
            while((len=fis.read(buf)) != -1){
                out.write(buf);
                Thread.sleep(500);
                System.out.println("Producer=" + i++);

            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }






}

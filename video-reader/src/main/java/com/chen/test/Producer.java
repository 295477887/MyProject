package com.chen.test;

/**
 * @author: ChenJie
 * @date 2019/4/10
 */

import java.io.FileInputStream;
import java.io.PipedOutputStream;

/**
 * 生产者线程
 */
public class Producer extends Thread {
    //输出流
    private PipedOutputStream out = new PipedOutputStream();

    //构造方法
    public PipedOutputStream  getOut() {
        return out;
    }
    @Override
    public void run() {
        writeMessage();
    }

    private void writeMessage() {
        StringBuilder sb = new StringBuilder("Hello World!!!");
        try {
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

//            for(int i=0;i<100000000;i++){
////                out.write(sb.toString().getBytes());
////                out.flush();
//
//            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
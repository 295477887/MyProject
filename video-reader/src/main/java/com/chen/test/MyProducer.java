package com.chen.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PipedOutputStream;

/**
 * @author: ChenJie
 * @date 2019/4/4
 */
public class MyProducer extends Thread{
    private PipedOutputStream outputStream;
    private String inputFile;

    public MyProducer(PipedOutputStream outputStream,String inputFile) {
        this.outputStream = outputStream;
        this.inputFile  = inputFile;
    }

    @Override
    public void run() {

        FileInputStream fis = null;
        while(true){
            System.out.println("producer==");
            try {
                fis = new FileInputStream(inputFile);
                byte [] buf = new byte[1000];
                int len;
                while((len=fis.read(buf)) != -1){
                    outputStream.write(buf);
                    Thread.sleep(100);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



//        while (true) {
//            try {
//                for (int i = 0; i < 5; i++) {
//                    outputStream.write(i);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

}

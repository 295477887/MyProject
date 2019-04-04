package com.chen.test;

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


        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream();
        try {
            pis.connect(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new MyProducer(pos,inputFile).start();
        new MyConsumer(pis,outputFile).start();
    }

}

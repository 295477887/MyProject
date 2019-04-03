package com.chen.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: ChenJie
 * @date 2019/4/2
 */
public class TestPush {
    public static void main(String[] args) {

        String cmd = "cmd /c start F:\\study\\rtmp\\windows\\ffmpeg\\bin\\ffmpeg -re -i F:\\study\\rtmp\\windows\\orange.mp4 -vcodec libx264 -acodec aac -f flv rtmp://172.16.1.86:1935/live/home";
        String line =null;
        StringBuilder sb = new StringBuilder();
        Runtime runtime = Runtime.getRuntime();
        try {
            System.out.println(cmd);
            java.lang.Process process = runtime.exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while((line = bufferedReader.readLine())!= null){
                sb.append(line + "\n");
                System.out.println(line);
                process.destroy();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

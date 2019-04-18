package com.chen;

import com.navinfo.util.Convert;

import java.io.*;
import java.net.Socket;

/**
 * @author: ChenJie
 * @date 2019/4/16
 */
public class FileRead {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1",20000);
            OutputStream os = socket.getOutputStream();

            FileReader reader = new FileReader("F:\\study\\rtmp\\windows\\1078\\mini2.txt");
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            int i = 0;
            while((line = br.readLine()) != null){
                if(i==0){
                    line = line.substring(1);
                }
                System.out.println("line="+line);
                os.write(Convert.hexStringToBytes(line.trim()));
                os.flush();
                i++;
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
}

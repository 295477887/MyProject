package com.chen;

import com.navinfo.util.Convert;

import java.io.*;

/**
 * @author: ChenJie
 * @date 2019/4/16
 */
public class FileWrite {
    public static void main(String[] args) {
        try {
            File file = new File("F:\\study\\rtmp\\windows\\264\\1078.264");
            FileOutputStream fos = new FileOutputStream(file);

            FileReader reader = new FileReader("F:\\study\\rtmp\\windows\\1078\\mini2.txt");
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            int i = 0;
            while((line = br.readLine()) != null){
                if(i==0){
                    line = line.substring(1);
                }
                System.out.println("line="+line);
                fos.write(Convert.hexStringToBytes(line.trim().substring(60)));
                i++;
                Thread.sleep(10);
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

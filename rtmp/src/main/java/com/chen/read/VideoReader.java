package com.chen.read;

import com.chen.util.Convert;

import java.io.FileInputStream;

/**
 * @author: ChenJie
 * @date 2019/4/3
 */
public class VideoReader {
    public static void main(String[] args) {
        try {
            byte [] header = new byte[100];

//            Socket socket = new Socket("172.16.1.86",6666);
//            OutputStream os = socket.getOutputStream();
            FileInputStream fis = new FileInputStream("F:\\study\\rtmp\\windows\\orange.mp4");
            byte[] buf = new byte[900];
            int len;
            while ((len = fis.read(buf)) != -1)  {

                System.out.println(Convert.bytesToHexString(buf));
                System.out.println();
//                os.write(buf, 0, len);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

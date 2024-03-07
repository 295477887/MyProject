package com.chen.file;

import com.navinfo.util.Convert;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Base64;

/**
 * Created by chen on 2019/7/8.
 */
public class FileTest {

    @Test
    public void testRead() throws Exception {
//        FileReader reader = new FileReader(new File("C:\\Users\\c2954\\Documents\\Tencent Files\\295477887\\FileRecv\\user.der"));
//        BufferedReader br = new BufferedReader(reader);
//        String line = "";
//        int i = 0;
//        while((line = br.readLine()) != null){
//            System.out.println("line="+Convert.bytesToHexString(line.getBytes("utf-8")));
//        }

        FileInputStream fis = new FileInputStream(new File("C:\\Users\\c2954\\Documents\\Tencent Files\\295477887\\FileRecv\\user.der"));
        byte [] bytes = new byte[10240];
        int l = 0 ;
        String hex = "";
        FileOutputStream os = new FileOutputStream("d://user.der");
        while((l=fis.read(bytes))!= -1){
            os.write(bytes,0,l);
            hex = Convert.bytesToHexString(bytes) ;
        }
        System.out.println(hex);

        String result = new BASE64Encoder().encode(bytes);

        System.out.println(result);
    }

}

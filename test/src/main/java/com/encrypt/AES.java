package com.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.base.Stopwatch;
import org.apache.commons.codec.binary.Base64;

import java.util.concurrent.TimeUnit;

/**
 * 字符串加解密
 * Created by chen on 2019/10/17.
 */
public class AES {

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "1234567890123456";
        // 需要加密的字串
        String cSrc = "7E0200014A013900000003003100000000000C00010000000000000000000000000000171114164811010400000000030200002504000000002A020000300113310101EF0800000000000000E8F2020000F4020000F6020000F707FFFFFFFFFFFFFFF8020000F9020000FB020000790B0000000000000000000000E20100E6D70009012A000201090003010A0004010B0005020D0C0001020706002401890025028B8A0026018C000E0139000F013A0021047C7B7A79002204807F7D027D010020010100080428272625000601110007011C000A0102000B023332000C0101000D01010014025A590015025C5B0016025E5D001C0169001D026B6A001E026D6C001F016E001701610018016200190163001A0164001B0267660010024E4D001102504F0023010100120454535251001304585756550027019A00270192002804A4A3A2A1002904A8A7A6A5002A0400000003002B04000007C6897E";
        System.out.println(cSrc);
        // 加密
        Stopwatch stopwatch = Stopwatch.createStarted();
        String enString = AES.Encrypt(cSrc, cKey);
        stopwatch.stop();
        System.out.println("加密耗时："+stopwatch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println("加密后的字串是：" + enString);

        // 解密
        Stopwatch stopwatch1 = Stopwatch.createStarted();
        String DeString = AES.Decrypt(enString, cKey);
        stopwatch1.stop();
        System.out.println("解密耗时："+stopwatch1.elapsed(TimeUnit.MILLISECONDS));
        System.out.println("解密后的字串是：" + DeString);
    }
}

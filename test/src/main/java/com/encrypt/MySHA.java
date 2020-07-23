package com.encrypt;

import java.security.MessageDigest;

public class MySHA {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        String msg = "0123456789abcdef";

        {
            MessageDigest sha = MessageDigest.getInstance("SHA");
            sha.update(msg.getBytes());
            byte []shaBin = sha.digest();
            printBytes(shaBin);
        }

        {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update(msg.getBytes());
            byte []sha1Bin = sha1.digest();
            printBytes(sha1Bin);
        }

        {
            MessageDigest sha224 = MessageDigest.getInstance("SHA-224");
            sha224.update(msg.getBytes());
            byte []sha224Bin = sha224.digest();
            printBytes(sha224Bin);
        }

        {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(msg.getBytes());
            byte []sha256Bin = sha256.digest();
            printBytes(sha256Bin);
        }

        {
            MessageDigest sha384 = MessageDigest.getInstance("SHA-384");
            sha384.update(msg.getBytes());
            byte []sha384Bin = sha384.digest();
            printBytes(sha384Bin);
        }

        {
            MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            sha512.update(msg.getBytes());
            byte []sha512Bin = sha512.digest();
            printBytes(sha512Bin);
        }
    }

    /**
     * 十六进制打印字节数组
     * @param b byte[]
     */
    public static void printBytes(byte[] b)
    {
        for(int i=0;i<b.length;i++)
        {
            System.out.printf("%02X", b[i]);
        }
        System.out.println();
    }
}
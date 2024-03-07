package com.chen.poiUtil;

import org.apache.poi.poifs.crypt.Decryptor;

/**
 * @author: ChenJie
 * @date 2019/1/19
 */
public class DeThread implements Runnable{
    String [] s={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private static final int LENGTH = 62;
    private Decryptor decryptor;

    public DeThread(Decryptor decryptor) {
        this.decryptor = decryptor;
    }

    public void run() {
        try {
            String prod ;
            int count = 0;
            long start = System.currentTimeMillis();
            //word文件解密,返回解密结果
            for(int i=0;i<LENGTH;i++){
                for(int j=0;j<LENGTH;j++){
                    for(int k=0;k<LENGTH;k++){
                        for(int l=0;l<LENGTH;l++){
                            for(int m=0;m<LENGTH;m++){
                                count++;
                                prod=s[i]+s[j]+s[k]+s[l]+s[m];
                                boolean flag = decryptor.verifyPassword(prod);
                                if (flag) {
                                    System.out.println("解密成功,密码为:"+prod);
                                    break;
                                }
                            }
                            System.out.println(Thread.currentThread().getName()+" 已破解"+count
                                    +"个，当前速度："+count *1000  / (System.currentTimeMillis() - start + 1) +"个/s");
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

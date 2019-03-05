package com.chen.poiUtil;

import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.concurrent.*;

/**
 * @author: ChenJie
 * @date 2019/1/19
 */
public class DecryptExcel {
    private static  String [] s={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private static final int LENGTH = 62;


    public static void descExcel() throws FileNotFoundException {

        System.out.println("begin");

        String excelPath = "d:\\test.xlsx";
        String password = "1234";
        String prefix = excelPath.substring(excelPath.lastIndexOf(".") + 1);

        Workbook workbook;

        InputStream inp = new FileInputStream(excelPath);

        try {
            if (prefix.toUpperCase().equals("XLS")) {
                org.apache.poi.hssf.record.crypto.Biff8EncryptionKey
                        .setCurrentUserPassword(password);
                workbook = WorkbookFactory.create(inp);
                inp.close();
            } else {
                POIFSFileSystem pfs = new POIFSFileSystem(inp);
                inp.close();
                EncryptionInfo encInfo = new EncryptionInfo(pfs);
                Decryptor decryptor = Decryptor.getInstance(encInfo);
                decryptor.verifyPassword(password);
                workbook = new XSSFWorkbook(decryptor.getDataStream(pfs));
                Sheet sheet = workbook.getSheetAt(0);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
//        String path = "d://test.xlsx";
        String path = args[0];
        File file = new File(path);
        InputStream is = null;
        String psd= "";
        try {
            is = new FileInputStream(file);
            POIFSFileSystem pfs = new POIFSFileSystem(is);
            is.close();
            EncryptionInfo ei = new EncryptionInfo(pfs);
            Decryptor decryptor = Decryptor.getInstance(ei);
//            Thread thread1 = new Thread(new DeThread(decryptor));
//            Thread thread2 = new Thread(new DeThread(decryptor));
//            Thread thread3 = new Thread(new DeThread(decryptor));
//            Thread thread4 = new Thread(new DeThread(decryptor));
//            Thread thread5 = new Thread(new DeThread(decryptor));
//            thread1.start();
//            thread2.start();
//            thread3.start();
//            thread4.start();
//            thread5.start();

            ExecutorService pool =new ThreadPoolExecutor(20, 20,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());

            for(int i=0;i<20;i++){
                pool.execute(new DeThread(decryptor));
            }


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     *
     * 函数的目的/功能   解密 xls 或 xlsx 文件,返回解密成功的密码
     * @param url
     * @param pwd
     * @return
     */
    public static String decryptXlsx(String url, String pwd) {
        File file = new File(url);
        InputStream is = null;
        String psd= "";
        try {
            is = new FileInputStream(file);
            POIFSFileSystem pfs = new POIFSFileSystem(is);
            is.close();
            EncryptionInfo ei = new EncryptionInfo(pfs);
            Decryptor decryptor = Decryptor.getInstance(ei);
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
                            System.out.println("已破解"+count
                                    +"个，当前速度："+count *1000  / (System.currentTimeMillis() - start + 1) +"个/s");
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return psd;
    }


}

package com.chen.poiUtil;

import com.jxcell.CellException;
import com.jxcell.View;

import java.io.IOException;

/**
 * @author: ChenJie
 * @date 2019/1/19
 */
public class EncryptDecryptUtil {

    /**
     * 读取excel，并进行加密
     *
     * @param url
     *            excel文件路径 例：D:\\word.xls
     * @param pwd
     *            加密密码
     */
    public static void encrypt(String url, String pwd) {
        View m_view = new View();
        try {
            // read excel
            m_view.read(url);
            // set the workbook open password
            m_view.write(url, pwd);
        } catch (CellException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * excel 解密
     *
     * @return void
     * @author lifq
     * @date 2015-3-13 下午02:15:49
     */
    public static void decrypt(String url, String pwd) {
        View m_view = new View();
        try {
            // read the encrypted excel file
            m_view.read(url, pwd);

            // write without password protected
            m_view.write(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) {
        // 下面1与2 两个方法请分开执行，可以看到效果
        //
        //1. 把g:\\test.xls 添加打开密码123
        EncryptDecryptUtil.encrypt("d:\\test.xls", "123");
        //2. 把g:\\test.xls 密码123 去除
//        EncryptDecryptUtil.decrypt("g:\\test.xls", "123");

    }
}
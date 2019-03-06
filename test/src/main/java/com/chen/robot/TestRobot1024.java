package com.chen.robot;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @author: ChenJie
 * @date 2019/3/5
 */
public class TestRobot1024 {
    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("========================"+sdf.format(new Date())+"========================");

            int x = 630;
            int y = 450;
            Thread.sleep(6000);
            int count = 1;
            for(int i=0;i<1000000;i++){
                Robot robot = new Robot();
                robot.mouseMove(x,y);

                Toolkit tk = Toolkit.getDefaultToolkit(); // 获取缺省工具包
                Dimension di = tk.getScreenSize(); // 屏幕尺寸规格
                System.out.println("长宽为:"+di.width +" , "+di.height);
                Rectangle rec = new Rectangle(0, 0, di.width, di.height);
                BufferedImage bi = robot.createScreenCapture(rec);
                int centerColorRGB = bi.getRGB(620, 420);
                int leftColorRGB = bi.getRGB(300, 300);
                Color centerColor =new Color(16777216 + centerColorRGB);
                Color leftColor =new Color(16777216 + leftColorRGB);
                System.out.println(i+" 中心颜色："+centerColor + ", 左侧颜色："+leftColor);
                //左侧是否是黑色
                boolean isLeftDark = leftColor.getBlue()<50 && leftColor.getGreen() < 50 && leftColor.getRed() < 50;
                boolean isCenterDark = centerColor.getBlue()<50 && centerColor.getGreen() < 50 && centerColor.getRed() < 50;
                //中间出现白色提示框 并且 左侧是黑色
                if(centerColor.equals(Color.white) && isLeftDark){
                    robot.mousePress(KeyEvent.BUTTON1_MASK);
                    robot.mouseRelease(KeyEvent.BUTTON1_MASK);
                    System.out.println("点击："+count++);
                }
                else if(isLeftDark && isCenterDark){
                    robot.mousePress(KeyEvent.BUTTON1_MASK);
                    robot.mouseRelease(KeyEvent.BUTTON1_MASK);
                    System.out.println("网络中断/学习完成！");
                    sendMail();
                    break;
                }
                Thread.sleep(10*1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送邮件
    public static  void sendMail() throws Exception {
        // 收件人电子邮箱
        String to = "18263043830@139.com";

        // 发件人电子邮箱
        String from = "18263043830@139.com";

        // 指定发送邮件的主机为 localhost
        String host = "smtp.139.com";

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.transport.protocol", "SMTP");
        properties.put("mail.smtp.auth", "true");
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("18263043830@139.com", "chen@0120");
            }
        };
        Session session = Session.getInstance(properties, auth);
        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("课程已学完!");

            // 设置消息体
            message.setText("课程已学完!");

            // 发送消息
            Transport.send(message);
            System.out.println("邮件发送成功....");
        }catch (Exception mex) {
            mex.printStackTrace();
        }
    }

}
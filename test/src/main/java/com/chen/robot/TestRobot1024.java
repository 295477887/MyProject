package com.chen.robot;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author: ChenJie
 * @date 2019/3/5
 */
public class TestRobot1024 {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static Properties properties;


    public static void main(String[] args) {
        try {
            Thread.sleep(10000);

            Robot robot = new Robot();

            //点击 金牌课程  507 107
            robot.mouseMove(507,107);
            mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,20);

            Thread.sleep(5000);
            //点击输入框 820  435
            robot.mouseMove(820,435);
            mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,20);

            //课程名配置文件
            int courseSize = properties.size();
            for(int c=1; c<=courseSize; c++){
                String courseName = getProperty(c + "");
                //把课程名粘贴到输入框
                StringSelection stringSelection = new StringSelection(courseName);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

                // ctrl + v
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);


                //点击搜索
                robot.mouseMove(963,436);
                mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,20);

                Thread.sleep(5000);

                //点击课程
                robot.mouseMove(160,550);
                mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,20);

                Thread.sleep(5000);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 学习课程
     * */
    public static void study(Robot robot) throws Exception{
        int x = 630;
        int y = 450;
        int count = 1;
        for(int i=0;i<1000;i++){
            System.out.println("========================"+sdf.format(new Date())+"========================");


            //鼠标移动，防止长时间不动被检测到
            robot.mouseMove(200,200);
            robot.mouseMove(x,y);

            Toolkit tk = Toolkit.getDefaultToolkit();
            // 屏幕尺寸规格
            Dimension di = tk.getScreenSize();
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
                mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,20);
                System.out.println("点击："+count++);
            }
            else if(isLeftDark && isCenterDark){
                mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,20);
                System.out.println("网络中断/学习完成！");
                sendMail();
                break;
            }
            Thread.sleep(10*1000);
        }
    }


    private static void mouseLeftClick(Robot robot, int button1Mask, int delay) {
        robot.mousePress(button1Mask);
        robot.delay(delay);
        robot.mouseRelease(button1Mask);
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


    static
    {
        properties = new Properties();
        InputStream inputStream = null;
        BufferedReader br = null;
        try
        {
            inputStream = TestRobot1024.class.getClassLoader().getResourceAsStream("course.properties");
            br = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            properties.load(br);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                if(inputStream != null)
                {
                    inputStream.close();
                }
                if(br != null)
                {
                    br.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        }

    }
    public static String getProperty(String key)
    {
        return properties.getProperty(key);
    }

}
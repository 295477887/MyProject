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
public class TestRobot1024New {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static Properties properties;


    public static void main(String[] args) {
        try {
            Thread.sleep(10000);

            Robot robot = new Robot();

            
            //�γ��������ļ�
            int courseSize = properties.size();
            for(int c=1; c<=100; c++){

				String courseName = getProperty(c + "");
				if(courseName == null || courseName.equals("")){
					continue;
				}
				courseName = courseName.trim();

				//��� ���ƿγ�  507 107
				robot.mouseMove(507,107);
				mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,20);
				System.out.println("������ƿγ�");
				Thread.sleep(2000);

				//��� ȫ��
				robot.mouseMove(50,180);
				mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,20);
				System.out.println("���ȫ��");
				Thread.sleep(2000);

				//��������,��� 820  435
				robot.mouseMove(990,310);
				mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,200);
				mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,200);
				mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,200);
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.delay(100);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				System.out.println("��������");
				Thread.sleep(1000);

				
				robot.mouseMove(990,310);
				mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,200);
				Thread.sleep(1000);
				


                //�ѿγ���ճ���������
                StringSelection stringSelection = new StringSelection(courseName);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

                // ctrl + v
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
				Thread.sleep(1000);
				System.out.println("ճ���γ���:��"+courseName+"��");


                //�������
                //robot.mouseMove(963,436);
                //mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,20);
				//�س�
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.delay(200);
                robot.keyRelease(KeyEvent.VK_ENTER);
				System.out.println("����");
                Thread.sleep(2000);

                //����γ�
                robot.mouseMove(60,420);
                mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,20);
				System.out.println("����γ�");
                Thread.sleep(5000);
				study(robot,courseName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ѧϰ�γ�
     * */
    public static void study(Robot robot,String courseName) throws Exception{
        int x = 630;
        int y = 450;
        int count = 1;
		int dark = 0;
        for(int i=0;i<1000;i++){
            System.out.println("========================"+sdf.format(new Date())+"========================");


            //����ƶ�����ֹ��ʱ�䲻������⵽
            robot.mouseMove(200,200);
			Thread.sleep(200);
            robot.mouseMove(x,y);

            Toolkit tk = Toolkit.getDefaultToolkit();
            // ��Ļ�ߴ���
            Dimension di = tk.getScreenSize();
            //System.out.println("����Ϊ:"+di.width +" , "+di.height);
            Rectangle rec = new Rectangle(0, 0, di.width, di.height);
            BufferedImage bi = robot.createScreenCapture(rec);
            int centerColorRGB = bi.getRGB(500, 550);
            int leftColorRGB = bi.getRGB(210, 550);
            Color centerColor =new Color(16777216 + centerColorRGB);
            Color leftColor =new Color(16777216 + leftColorRGB);
            System.out.println(i+" ������ɫ��"+centerColor + ", �����ɫ��"+leftColor);
            //����Ƿ��Ǻ�ɫ
            boolean isLeftDark = leftColor.getBlue()<21 && leftColor.getGreen() < 21 && leftColor.getRed() < 21;
            boolean isCenterDark = centerColor.getBlue()==0 && centerColor.getGreen() ==0 && centerColor.getRed() ==0;
            //�м���ְ�ɫ��ʾ�� ���� ����Ǻ�ɫ
            if(centerColor.equals(Color.white) && isLeftDark){
                mouseLeftClick(robot,KeyEvent.BUTTON1_MASK,20);
                System.out.println("�����һ�Σ�"+count++);
            }
            else if(isLeftDark && isCenterDark){
				dark++;
				if(dark >=3){
					System.out.println(courseName+",ѧϰ��ɣ�");
					sendMail(courseName);
					break;     
				}
            }
            Thread.sleep(10*1000);
        }
    }


    private static void mouseLeftClick(Robot robot, int button1Mask, int delay) {
        robot.mousePress(button1Mask);
        robot.delay(delay);
        robot.mouseRelease(button1Mask);
    }

    //�����ʼ�
    public static  void sendMail(String courseName) throws Exception {
        // �ռ��˵�������
        String to = "18263043830@139.com";

        // �����˵�������
        String from = "18263043830@139.com";

        // ָ�������ʼ�������Ϊ localhost
        String host = "smtp.139.com";

        // ��ȡϵͳ����
        Properties properties = System.getProperties();

        // �����ʼ�������
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
            // ����Ĭ�ϵ� MimeMessage ����
            MimeMessage message = new MimeMessage(session);

            // Set From: ͷ��ͷ�ֶ�
            message.setFrom(new InternetAddress(from));

            // Set To: ͷ��ͷ�ֶ�
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: ͷ��ͷ�ֶ�
            message.setSubject(courseName+" ��ѧ��!");

            // ������Ϣ��
            message.setText(courseName+" ��ѧ��!");

            // ������Ϣ
            Transport.send(message);
            System.out.println("�ʼ����ͳɹ�....");
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
            inputStream = TestRobot1024New.class.getClassLoader().getResourceAsStream("course.properties");
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
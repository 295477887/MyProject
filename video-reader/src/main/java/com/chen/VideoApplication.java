package com.chen;

import com.chen.javacv.Player;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootApplication
public class VideoApplication {

	public static void main(String[] args) {

		SpringApplication.run(VideoApplication.class, args);
		try {
			int count = 0;

			while (true) {
				// "GET /ipcam/jpeg.cgi HTTP/1.1\r\n\r\nAuthorization: Basic YWRtaW46OTk5OQ====\r\n\r\n"
				// 创建Socket对象
//                Socket socket = new Socket("192.168.0.80", 8080);

				// 根据输入输出流和服务端连接
//                OutputStream outputStream = socket.getOutputStream(); // 获取一个输出流，向服务端发送信息
				// "GET /ipcam/jpeg.cgi HTTP/1.1\r\nAuthorization: Basic YWRtaW46OTk5OQ====\r\n\r\n"

//                outputStream.write("GET /ipcam/avc.cgi HTTP/1.1\r\nAuthorization: Basic YWRtaW46OTk5OQ====\r\n\r\n".getBytes());
//                outputStream.flush();
//                socket.shutdownOutput(); // 关闭输出流

//                InputStream inputStream = socket.getInputStream(); // 获取一个输入流，接收服务端的信息
				InputStream inputStream = new FileInputStream("C:\\Users\\dell\\Desktop\\2.wmv");
				String outputFile = "rtmp://172.16.1.86:1935/live/home";
				Player.frameRecord(inputStream, outputFile, 1);
				count++;
				System.out.println("================================" + count +
						"次数");
//                socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.chen;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RtmpServerApplicationTests {



//	private static final String IP = "172.16.1.86";
	private static final String IP = "172.16.1.223";
	private static final int PORT = 20000;




	public static void main(String[] args) {
		System.out.println("---------------------------");
		VideoReadThread videoReadThread1 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-1.264", "013800138000", 1, IP, PORT);
		VideoReadThread videoReadThread2 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-2.264", "013800138000", 2, IP, PORT);
		VideoReadThread videoReadThread3 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-3.264", "013800138000", 3, IP, PORT);
		VideoReadThread videoReadThread4 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-4.264", "013800138000", 4, IP, PORT);
		VideoReadThread videoReadThread5 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-5.264", "013800138000", 5, IP, PORT);
		VideoReadThread videoReadThread6 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-6.264", "013800138000", 6, IP, PORT);
		VideoReadThread videoReadThread7 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-7.264", "013800138000", 7, IP, PORT);
		VideoReadThread videoReadThread8 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-8.264", "013800138000", 8, IP, PORT);
		VideoReadThread videoReadThread9 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-9.264", "013800138000", 9, IP, PORT);
		VideoReadThread videoReadThread10 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-10.264", "013800138000", 10, IP, PORT);
		VideoReadThread videoReadThread11 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-11.264", "013800138000", 11, IP, PORT);
		VideoReadThread videoReadThread12 = new VideoReadThread("F:\\study\\rtmp\\windows\\264\\640-12.264", "013800138000", 12, IP, PORT);

		videoReadThread1.start();
		videoReadThread2.start();
		videoReadThread3.start();
		videoReadThread4.start();
		videoReadThread5.start();
		videoReadThread6.start();
		videoReadThread7.start();
		videoReadThread8.start();
		videoReadThread9.start();
		videoReadThread10.start();
		videoReadThread11.start();
		videoReadThread12.start();

	}

//	private static void sendBinContent(String fileName, String tid,int channelId) {
//		byte [] content = new byte[CONTENT_SIZE];
//		//头信息
//		byte [] header = new byte[30];
//		fixHeader(header,tid,channelId);
//		try{
//			Socket socket = new Socket(IP,PORT);
//			OutputStream os = socket.getOutputStream();
//			FileInputStream fis = new FileInputStream(fileName);
//			byte [] buf = new byte[BODY_SIZE];
//			int length;
//			int i=0;
//			while((length = fis.read(buf)) != -1){
//				System.out.println(Convert.bytesToHexString(buf));
//				//包序号
//				ArraysUtils.arrayappend(header,6,Convert.intTobytes(i++,2));
//				//时间戳 ms
//				ArraysUtils.arrayappend(header,16,Convert.longTobytes(i*100,8));
//				ArraysUtils.arrayappend(content,0,header);
//				ArraysUtils.arrayappend(content,30,buf);
////                System.out.println("=="+Convert.bytesToHexString(content));
//				os.write(content);
//				os.flush();
//				Thread.sleep(100);
//			}
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	private static void fixHeader(byte [] header,String tid,int channelId){
//		//帧头标识
//		ArraysUtils.arrayappend(header,0, Constants.DELIMITER);
//		//v p x cc
//		byte [] v = new byte[]{1,0,  0,  0,  0,0,0,1};
//		ArraysUtils.arrayappend(header,4, Convert.longTobytes(Convert.binary2Long(v,1),1));
//		//M PT
//		byte [] m = new byte[]{1,  0,0,0,0,0,0,0};
//		ArraysUtils.arrayappend(header,5, Convert.longTobytes(Convert.binary2Long(m,1),1));
//
//		//sim卡号
//		ArraysUtils.arrayappend(header,8,Convert.hexStringToBytes(tid));
//		//逻辑通道号
//		ArraysUtils.arrayappend(header,14,Convert.intTobytes(channelId,1));
//		//数据类型 ００００：视频 Ｉ 帧；,０００１：视频 Ｐ 帧；,００１０：视频 Ｂ 帧；,００１１：音频帧；,０１００：透传数据,
//		//分包处理标记  ００００：原子包，不可被拆分；,０００１：分包处理时的第一个包；,００１０：分包处理时的最后一个包；,００１１：分包处理时的中间包
//		ArraysUtils.arrayappend(header,15,Convert.intTobytes(0,1));
//
//		//Last I Frame Interval
//		ArraysUtils.arrayappend(header,24,Convert.longTobytes(100,2));
//		//Last Frame Interval
//		ArraysUtils.arrayappend(header,26,Convert.longTobytes(100,2));
//		//数据体长度
//		ArraysUtils.arrayappend(header,28,Convert.longTobytes(BODY_SIZE,2));
//	}


}

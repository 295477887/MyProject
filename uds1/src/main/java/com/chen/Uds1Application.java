package com.chen;

import com.alibaba.fastjson.JSON;
import com.faw_qm.engineer_online.define.SigNal;
import com.faw_qm.engineer_online.exception.InitException;
import com.faw_qm.engineer_online.exception.ProtclParseException;
import com.faw_qm.engineer_online.exception.ReadFileException;
import com.faw_qm.engineer_online.impl.ProtocolParserImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

import java.util.*;


@SpringBootApplication
public class Uds1Application {

    public static void main(String[] args) throws InitException, ProtclParseException, ReadFileException {
//		SpringApplication.run(Uds1Application.class, args);


        /**
         * 解析流
         * */
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		ProtocolParserImpl protocolParserImpl = ProtocolParserImpl.getInstance();
//		String filePath = "E:\\idea-space\\MyProject\\uds1\\src\\main\\resources\\";
//		protocolParserImpl.init(filePath, "J7_EDC17_762");
//		ArrayList lstRet = new ArrayList();
//		String strRequest3 = "";
//		String[] strInfo={""};
//		stopWatch.stop();
//		StopWatch stopWatch2 = new StopWatch();
//		stopWatch2.start();
//		String lineTxt = "11572.251525 18DA00F1 00 03 19 02 80\\\\n11572.261127 18DAF100 00 13 59 02 80 00 E7 00 80 00 E9 00 80 00 EA 00 80 00 EB 00 80";
//		String[] split = lineTxt.split("\\\\n");
//		for(String txt : split){
//			lstRet.add(ProtocolParserImpl.Format_ASCII2Struct1(txt, strRequest3));
//		}
//		List<SigNal> sigNals = ProtocolParserImpl.Parser_PMsg2SignalAry(lstRet, strInfo);
//		stopWatch2.stop();
//		System.out.println(JSON.toJSONString(sigNals));
//		System.out.println(sigNals);
//		System.out.println("耗时："+stopWatch.getTotalTimeMillis());
//		System.out.println("耗时2："+stopWatch2.getTotalTimeMillis());

        /**
         * 解析1个文件
         * */
//		//建立一个报文文件解析实例
//		ProtocolParserImpl protocolParserImpl = ProtocolParserImpl.getInstance();
//		String filePath = "C:\\Users\\c2954\\Desktop\\";
//		//执行初始化
//		protocolParserImpl.init(filePath, "J7_ECAS");
//		String[] strInfo={""};
//		//生成解析列表
//		String fileName = "1.txt";
//		List<SigNal> list = protocolParserImpl.parser(fileName,strInfo);
//		//在控制台打印解析结果清单
//		System.out.println(list);
//		System.out.println(JSON.toJSONString(list));

        /**
         * 加载多个数据库
         * 解析多个文件
         * */


        long start = System.currentTimeMillis();
        String filePath = "/";
        String[] ecus = {"J7_12TA_AMT", "J7_ABS_8", "J7_ABS_E8", "J7_ABS_KNORR", "J7_ABS_WABCO", "J7_ACC", "J7_AMT_ZF", "J7_BCM", "J7_DCM", "J7_EBS_KNORR", "J7_EBS_WABCO", "J7_ECAS", "J7_EDC17_762", "J7_EMSV", "J7_EMSVI", "J7_EMS_Bosch", "J7_EPB_RuiLi", "J7_FM", "J7_GW", "J7_IC", "J7_LDW", "J7_PS", "J7_RCU_Voith", "J7_RCU_ZF", "J7_TPMS", "J7_VCU"};
        List<String> ecuTypes = Arrays.asList(ecus);
        Map<String, ProtocolParserImpl> map = new HashMap();
        for (String ecuType : ecuTypes) {
            ProtocolParserImpl protocolParserImpl = new ProtocolParserImpl();
            protocolParserImpl.init(filePath, ecuType);
            map.put(ecuType, protocolParserImpl);
        }
        long end1 = System.currentTimeMillis();
        String strRequest3 = "";
        String[] strInfo = {""};

        List<String> subList = new ArrayList();
        subList.add("J7_EDC17_762##11572.251525 18DA00F1 00 03 19 02 80\\\\n11572.261127 18DAF100 00 13 59 02 80 00 E7 00 80 00 E9 00 80 00 EA 00 80 00 EB 00 80");
        subList.add("J7_ECAS##678.533142 18DA2FF1 00 04 18 02 FF FF\\\\n678.545776 18DAF12F 00 08 58 02 00 18 E3 00 19 E3\\\\n");
        subList.add("J7_EMSV##1595415012.456657 18da00f1 00 03 22 f1 82\\\\n1595415012.722587 18daf100 00 2b 62 f1 82 33 36 30 31 36 31 31 44 37 35 48 42 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00\\\\n1595415012.773001 18da00f1 00 03 22 f1 87\\\\n1595415012.882539 18daf100 00 0f 62 f1 87 33 36 30 31 31 31 35 2d 39 31 45 58\\\\n1595415012.933210 18da00f1 00 03 22 f1 95\\\\n1595415013.042511 18daf100 00 10 62 f1 95 46 41 57 45 34 5f 56 30 34 2e 32 30 30\\\\n");
        subList.add("J7_EMSV##1595416871.987555 18da00f1 00 03 22 f1 82\\\\n<!TimeOut>\\\\n1595416877.039231 18da00f1 00 03 22 f1 87\\\\n<!TimeOut>\\\\n1595416882.090325 18da00f1 00 03 22 f1 95\\\\n<!TimeOut>\\\\n");
        subList.add("J7_TPMS##1595415150.702999 18da33f1 00 03 19 02 08\\\\n<!TimeOut>");
        List<String> udsList = new ArrayList();
        for (int i = 0; i < 2000; i++) {
            udsList.addAll(subList);
        }


        for (String content : udsList) {
            String[] cons = content.split("##");
            String ecu = cons[0];
            String lineTxt = cons[1];
            ArrayList lstRet = new ArrayList();
            String[] split = lineTxt.split("\\\\n");
            for (String txt : split) {
                lstRet.add(ProtocolParserImpl.Format_ASCII2Struct1(txt, strRequest3));
            }
            ProtocolParserImpl protocolParser = map.get(ecu);
            List<SigNal> sigNals = protocolParser.Parser_PMsg2SignalAry(lstRet, strInfo);
//			System.out.println(JSON.toJSONString(sigNals));
        }
        long end2 = System.currentTimeMillis();
        System.out.println("总条数 加载数据库耗时 总耗时 平均耗时");
        System.out.println(udsList.size() + " " + (end1 - start) + " " + (end2 - end1) + " " + (end2 - end1) / udsList.size());


        /**
         * 不知道ecutype循环解析
         * */
//		String [] ecus = {"J7_12TA_AMT","J7_ABS_8","J7_ABS_E8","J7_ABS_KNORR","J7_ABS_WABCO","J7_ACC","J7_AMT_ZF","J7_BCM","J7_DCM","J7_EBS_KNORR","J7_EBS_WABCO","J7_ECAS","J7_EDC17_762","J7_EMSV","J7_EMSVI","J7_EMS_Bosch","J7_EPB_RuiLi","J7_FM","J7_GW","J7_IC","J7_LDW","J7_PS","J7_RCU_Voith","J7_RCU_ZF","J7_TPMS","J7_VCU"};
//		for(int i = 0;i<ecus.length;i++){
//			//建立一个报文文件解析实例
//			ProtocolParserImpl protocolParserImpl = ProtocolParserImpl.getInstance();
//			String filePath = "E:\\idea-space\\MyProject\\uds1\\src\\main\\resources\\";
//			//执行初始化
//			protocolParserImpl.init(filePath, ecus[i]);
//			String[] strInfo={""};
//			//生成解析列表
//			String fileName = "uds.txt";
//			List<SigNal> list = protocolParserImpl.parser(fileName,strInfo);
//			//在控制台打印解析结果清单
//			System.out.println(i+"  "+ecus[i]+"= "+list);
//		}

    }

}

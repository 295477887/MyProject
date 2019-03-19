package com.chen.util;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.chen.pojo.Data;
import com.chen.pojo.CourseRate;
import java.util.List;


/**
 * @author: ChenJie
 * @date 2019/3/18
 */
public class HttpUtil {
    public static final String CHARSET="UTF-8";

    public static void main(String[] args) throws Exception{
        //领导和管理力
        String [] ids = {"a79cae5c-18e0-4a9c-a384-f26c6201529f","d1d53307-e3b6-49d6-9c90-48864f220612","b26b03b2-fadf-4723-a0ee-954eecf641bc","76e13f92-9028-4b39-be7d-c75aea7461ef","f5f45e57-0d21-45ea-a60c-a9dec4ef4bf5","6de1fe39-d5cf-47d0-8e81-bc6220abb457","a1bab5ab-bd71-40ba-9208-9a1f9957dba0","be954cd6-6eb8-4af5-b6ba-639c987fa2dc","1138e29a-fe8a-47a1-996c-3d93d514dd57","4296a016-baf6-4787-bdd0-bd263c0b77c1","311c27b0-ecac-4ee6-9f04-df2f431323dd","e43f4184-2ff2-4342-a95d-f5e0a025090f","4be9ea47-975a-437b-b8d3-c1d26be2dbcb","1c28a6ff-dbf4-4ec1-82e7-06da54a4634a","f307576b-b0a0-4fa1-83c0-02d93f712a3a","0dffb93c-bd4e-4390-8f84-527bb7514bfe","f0fb9917-91a2-412a-803e-6e3b51f71ccd","9560011a-c647-4374-8439-6a5003e5de6c","2cc5c8ce-7b2a-4411-ba42-00cdb875492b","4ca89f72-903a-4869-86d7-539782dce83d","447670b3-ec07-4547-860b-d7c7f0dca58c","6469d701-05e9-405f-88a0-1771a98f37f6","70e2f653-f2b2-4035-afb6-2c3074efefc6","6e86ed8f-7d57-4a04-b560-be33dfee669d","d302b8e9-5819-43e2-bf08-12492b0d4b9c","fb68753f-9cae-43ee-a7a5-0f656b56f9a1","08e2ecae-b5a7-4382-9a2d-f3cd1b2d73f6","f255eeaf-3706-48f2-8b10-4b0c2a36e3d3","d9a9842c-5138-40bf-989e-57cbfb6eb4c9","7feea500-7108-4a1b-950b-ed52c255a6ae","5ea28a28-8292-43ec-8ac0-f4dd29c71b53","283f0491-4b04-412a-b0dc-a5ca9faa851a","02d8ef76-be10-4116-a15a-b984a5ad2465","f844d59a-0570-4c02-88bc-b4bb7af086d8","58009d58-2cab-445a-9841-f4b8bb822e4f","9ae1505c-aec7-46a4-b759-de7d14670378","8ec433ab-f484-4f02-ae79-1322dd69fd37","455313ee-202e-419f-b4a4-a7e402856ef4","3f7df2b3-5ae2-4910-93de-ad4aa9c139b1","56e4cad4-d571-4070-be42-141d648c0b44","9e4dbd46-e1a4-446a-895b-b09e31a69e1f","00538cf0-9c36-4428-bbfc-0c8338d48dc7","b1a2a677-7df2-4a89-8216-4bfe014324cb","725d8ec7-6c97-437a-bbec-67f6cae1c19e","132d588d-3b77-4a83-847b-638ac9195c44","5f287d2b-451d-4a15-bffc-bc95abd415c2","37e0466c-216f-498b-a387-15731754d346","c7612583-527f-44c7-8844-fdce6f4e52c4","2421b679-8efd-400a-8b0f-95bb04cb89d0","30da25a0-ade0-46d0-af73-92eebd95e118","57f4a72b-0821-4310-8f66-b3ae5314bf7e","5f5ea181-09fe-404b-b813-31b879c3f205","5f5fe327-c689-4a0b-b31f-5b515843bfbd","ac522861-f107-49ea-aa91-010bc94f0ec3","ca99824b-4d68-47e8-85ce-bbfeb63fa1e3","deb2a35d-ef80-4add-a608-cf6aea3b8d0b","ed5a4b92-82c8-4d86-a99c-5bc305fedd22","dd3c7932-2633-41be-a9cc-1de5b450da1b","49f61bf3-c923-4577-be33-dd6b6480724e","60b5cd5b-7cc5-412f-9dad-2bdeb709fb70","b04a3d1c-b656-4533-9933-82c06bf2e18e","f7a4055d-0125-462e-8124-7af10a7a9101","d71d7536-9cfa-46fd-83e2-3f89d2ec6800","abfcb3ae-55ee-4028-8ce6-dbed2ea62fdc","16cf9313-c384-4615-952b-96f97158bb53","23ee4926-88f4-4049-90fd-9765d5151a8d","4fd2c9ff-3251-4b4f-8fec-bc6934cbe152","681da44d-4e0c-470e-894c-cfe95ea0acf5","8a4a50e1-02a0-4e31-b5bc-2f2bd3ab1387","ab0dd344-cd5f-4a89-848a-d5350a4258d0","b7c44e96-d85a-47df-9eec-52afd369a45c","ccc99c08-6bb7-4c36-b327-78155a56bc7b","ec456a2f-443a-49de-8265-eacd86b966e9","f87f6372-9f25-49f8-a05d-d3ee57d32a76","374fc9dd-efa2-42c8-8066-1abb6de31e60","5c594513-b706-4b5f-9ac9-463538c26a3d","912171b4-b21a-4fa4-b3b0-003f92bd10da","d5ae960f-cc00-4d05-bd44-5afdd9d7fe3d","06409c4c-9c84-4162-bed1-113198207327","7b453e9a-f9c8-4b90-9270-2d5b3fcde691","4bab3441-33d2-4b4b-8f95-1604609a9787","b0094c30-d2a2-4722-9918-284e9696963d","cbd61451-cf1a-46a7-a545-e9d552e08cbe","59c86050-67e2-48c2-a74e-4515217dadca","2273074b-9bde-4fe5-8f0d-2447a69d91dd","d405c5eb-43e6-4187-863b-fefc3b9db4dd","118595ae-83f9-43ed-a94c-0de106de6c37","ffc7cf0e-5c3a-461e-9e94-3b385a515557","589b2f17-ed64-4ce1-b4d3-2cfd6f8ba851","5dd60cb2-c705-4730-8874-8ac5f5981d17","4ba8d736-5973-408c-8cfa-727479bbf2fe","6ba15390-aa7e-4d54-961b-cc35d441a761","26935bea-ebd6-4e91-b75f-977c853d2987","392aa9cd-195e-42a2-a55c-be5e2fd2148b","18b6eae4-d5d3-486c-9dbb-45f266bc9b3c","ec7d1e88-e43b-41bc-8f67-0a96864a0363","9f4045a7-900f-4f1d-8fe5-80cb3ce8e394","81d2c2a5-c0e2-4458-b538-06a095936682","da98aaa8-205d-43d1-8283-aa7722ec1dbf","113ef64d-f795-401c-9b19-f07d87a1c5c0","5e609318-d290-45fc-8492-b25848d35a32","bdc961a4-f883-4652-a8ff-e4b4ab199e32","38d31150-c262-4115-ae46-f1217573dfeb","d1bf3cf7-3209-430b-9f88-1daadcecd40e","f54a9303-0c85-4b5b-808d-8686b3e96d2a","6cb8637f-c7d0-404b-8539-ad7cdf64bea1","8040be22-dcb4-47c4-8ceb-e0ecba595ffc","90926023-c9c0-4d7c-a3a8-a52af396a469","90f976b7-c835-4696-86c2-8accfb09f4a3","97047608-b5b5-4c7d-b6e6-4eb377d524b3","6f492a43-347a-4687-90a5-d84ebcf404a8","445e7261-786c-4874-aefa-e71fd722f856","31d22c51-2686-47dc-8769-60f6c1ff82ae","77613095-b910-425b-88a8-83a3b8438720","525153ab-750b-4924-8d79-fab0491d62de","bbe29e24-029b-4c63-9a09-5d9cd5985e97","ac93e583-60ae-47fa-a75b-2e61b1ae43c3","242215b9-0334-441b-9a77-c439d5e54d2d","bb4e6ea0-252c-40ce-9650-689a57134ff2","f67a9ce3-5019-4588-b882-1cde1134b27b","5336556b-601d-4a2f-8e47-4d3e35b884f5","df40576f-6074-45b9-8a4e-91d91a478d78","f4205cf4-502d-4479-b5e2-561824ce48f9","b3ca1044-e127-4b7d-8852-b852640efac0","425daf33-8edf-4243-a142-6f0190ea7fe9","7854c567-54d3-4f45-909a-9087f5313d0a","139c0d1e-a731-41a3-af41-92a7296bdb34","bab66817-9570-4537-a201-250e0cbb1daf","adae567d-9980-4677-a767-0db995b9a5c9","0e45c94a-017e-4a82-8f90-1733af3d22d4","380d4eb0-8e6e-4459-b7fe-d1d3fcec0336","69efcf10-a9da-4537-a99c-aae1e5b2f8d8","ff4cedd5-5f49-4aa2-b87b-27ed160cd6d1","5f48b8bc-e040-4afe-b271-536ab52e85b0","32456cea-a541-42b8-bcfe-f6e9beb03986","6339eb5a-f008-4511-90b1-64837f45a0f2","3503d4a3-2bcb-4c24-9c8e-b2ca3757d9c1","10398d42-5b5f-4df8-a402-45ac5355fb18","309f223c-d89b-4e16-a849-c9677c501dd3","c0fcb8e3-ef84-4093-9d06-72d57d0153ef","30067924-9650-44fb-aec7-feec071e24ed","7271a0f8-acc9-4e44-bd3a-27d45fcbe684","162a4ac7-8246-4726-946c-cb67ad241de8","97eea658-d320-40f1-b5d4-92fc462447ac","8deec02d-b07c-4e44-8ed4-a33bb4240cbd","d2022950-d350-4dc1-bfbd-f3e872830448","c70888fe-012d-439a-96a0-a7a20259d8c5","7bc110da-dc87-4b7f-9895-61257391c80a","3f9aa084-ddc3-480e-96ce-c7f3dd81f633","ae98cb08-5c35-4057-9ed4-41dc7c969067","04eb4318-4684-43b9-8aba-805ddb4cd850","87ce6aaf-6503-4eab-9a2a-ff368d1aeb7c","11c7d75c-09a0-4038-ad55-8708d8aeee38","b409e214-6266-44cb-8891-e62e98fd7b85","896cffb0-09e2-4e4a-9e8e-f37f4db9f3ce","0f4fcf62-0266-43a1-b6c9-1391bf13650f","94802ad9-6ccb-4bde-809d-062f8c814135","8d5d2a52-57e8-4712-850c-cf952277ebec","1569fab2-6ed0-4d83-88e4-d33699370a96","3897718c-0edd-4946-832a-76f20db7fb3a","a28cd24a-0f88-4c78-938d-714fb329b24a","c4524320-673d-4a5b-8cd5-f25253da7d0e","809143c1-8389-4404-a532-5e8ac6dc781d","1bd56c91-5a5e-4191-a111-935e81b24d0a","6a7e5e57-524c-4b97-94a9-16fba679b3bb","4d037836-8f84-4b29-b504-02fcdfe8fbc6","6955a7f5-067e-4094-8314-365578939fdc","e342241f-fbbd-483c-8395-d386b69266c2","80081976-a63e-44bf-9660-e9e296373950","17232cc4-618f-4583-8976-75480077167e","9b72b4cb-bb82-4043-b002-386d153841d3","d3a3fba8-376e-42f3-accc-1205e5d3ac50","bdaea74d-3321-4b28-a0ac-0f53f40a81cd","40af7fb0-9e76-4a1f-ac41-6cd488159284","71a5a5f4-6177-4dfd-bd93-ef015b9556dd","eda4c05e-1fb5-4119-8729-79ed688df7dc","2933346c-271c-47d4-8ada-04a0b3c194a5","ef2273dd-c6ef-448a-a8c3-da6e6ef91530","4ed17802-7b76-4480-ad38-a83188b12456","27411a1b-738d-490e-935d-604ce509e2df","94fa6188-7bd4-40ec-8b2d-6e627d92c926","92e5cbb9-e45f-4db8-8c36-27b6e7493de6","01fe8150-8abe-4a5c-bb11-c23afe0be5f3","592f8133-5fa0-414e-a3ad-7cadbddc1bfd","12eef4dc-46d5-4751-a09f-6aec5f7f1d74","776596bf-bbea-4445-9770-18ee68205326","98ec4d53-88f6-481e-a639-c0fe6031bede","f813bb72-f85e-4377-83ac-d83018a625bb","12c56877-396f-4f32-99ce-e35f2d23356d","1c5838d8-0294-4713-b63e-c47d0b2059f3","180bd8fe-db7f-49cb-bb36-89ace50110ed","0429181b-a737-4c13-ae68-61790e8dabf2","fc456533-2610-4d3a-bc10-1bef27531204","f82166be-b866-4cee-ab80-631ce3e4301d","110ab1c5-eaf6-4e85-b4a0-70d5b939358b","15ad39a1-c356-43cb-a017-4c03c1bc2526","48a54a27-d459-49b0-9f8a-83f65ff6b736","3895b25f-4d6c-4949-b72f-5ea55abbca82","02e6a15b-dab5-484f-aab0-b2912cd5a1df"};

        for(int j =0;j<ids.length;j++){
            String id = ids[j];
            String url = "https://jpapi.hz.taeapp.com/v1/orgs/0/knowledges/" + id + "/catalog";
            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("token", "AAAAAG5EHzcHic_KFNWkmAQS2aXMPLDAvcD55U0rSjZenGI9B5u-EmXwtuFrV7drMwvKT2kyPGpq4xQ33CGt1JyIOqQSoKCdbK3Q1PiL7NhwER9ogD7eKBfNw7Pr4CjmOd5AVovtVgfInN_Rfww0i7AEr1g");
            //必须设置false，否则会自动redirect到重定向后的地址
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            String result = getReturn(conn);
//        System.out.println("result="+result);
            Data data = JSON.parseObject(result, Data.class);
            if(data!= null){
                List<CourseRate> datas = data.getDatas();
                if(datas != null && datas.size()>0){
                    for(int i=0;i<datas.size();i++){
                        CourseRate courseRate = datas.get(i);
                        System.out.println(ids[j]+"="+j+"="+courseRate.getTitle()+"="+courseRate.getProgress()+"="+courseRate.getStudyHours());
                    }
                }
                else
                {
                    System.out.println("异常,课程为空");

                }
            }
            else{
                System.out.println("异常：返回结果为空");
            }
        }

//        URL serverUrl = new URL("https://jpapi.hz.taeapp.com/v1/orgs/0/knowledges/878f1332-e97f-4563-8bec-9d8fb6cd0f51/catalog");
//        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//        conn.setRequestProperty("token", "AAAAAG5EHzcHic_KFNWkmAQS2aXMPLDAvcD55U0rSjZenGI9B5u-EmXwtuFrV7drMwvKT2kyPGpq4xQ33CGt1JyIOqQSoKCdbK3Q1PiL7NhwER9ogD7eKBfNw7Pr4CjmOd5AVovtVgfInN_Rfww0i7AEr1g");
//        //必须设置false，否则会自动redirect到重定向后的地址
//        conn.setInstanceFollowRedirects(false);
//        conn.connect();
//        String result = getReturn(conn);
////        System.out.println("result="+result);
//        Data data = JSON.parseObject(result, Data.class);
//        if(data!= null){
//            List<CourseRate> datas = data.getDatas();
//            if(datas != null && datas.size()>0){
//                for(int i=0;i<datas.size();i++){
//                    CourseRate courseRate = datas.get(i);
//                    System.out.println(courseRate.getTitle()+"="+courseRate.getProgress());
//                }
//            }
//            else
//            {
//                System.out.println("异常,课程为空");
//
//            }
//        }
//        else{
//            System.out.println("异常：返回结果为空");
//        }

//        System.out.println("result="+data);
    }

    public static String  doGet(String url,String token)throws Exception{
        URL serverUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("token", token);
        //必须设置false，否则会自动redirect到重定向后的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();
        String result = getReturn(conn);
        return result;
    }

    /*请求url获取返回的内容*/
    public static String getReturn(HttpURLConnection connection) throws IOException {
        StringBuffer buffer = new StringBuffer();
        //将返回的输入流转换成字符串
        try(InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, CHARSET);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader)){
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            String result = buffer.toString();
            return result;
        }
    }

}

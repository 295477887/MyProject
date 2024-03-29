package com.navinfo.util;

import com.alibaba.fastjson.JSON;
import com.chen.pojo.CourseRate;
import com.chen.pojo.Data;
import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author: ChenJie
 * @date 2019/3/18
 */
public class HttpUtil {
    public static final String CHARSET="UTF-8";

    public void main1(String[] args) throws Exception{
        //领导和管理力
//        String [] ids = {"a79cae5c-18e0-4a9c-a384-f26c6201529f","d1d53307-e3b6-49d6-9c90-48864f220612","b26b03b2-fadf-4723-a0ee-954eecf641bc","76e13f92-9028-4b39-be7d-c75aea7461ef","f5f45e57-0d21-45ea-a60c-a9dec4ef4bf5","6de1fe39-d5cf-47d0-8e81-bc6220abb457","a1bab5ab-bd71-40ba-9208-9a1f9957dba0","be954cd6-6eb8-4af5-b6ba-639c987fa2dc","1138e29a-fe8a-47a1-996c-3d93d514dd57","4296a016-baf6-4787-bdd0-bd263c0b77c1","311c27b0-ecac-4ee6-9f04-df2f431323dd","e43f4184-2ff2-4342-a95d-f5e0a025090f","4be9ea47-975a-437b-b8d3-c1d26be2dbcb","1c28a6ff-dbf4-4ec1-82e7-06da54a4634a","f307576b-b0a0-4fa1-83c0-02d93f712a3a","0dffb93c-bd4e-4390-8f84-527bb7514bfe","f0fb9917-91a2-412a-803e-6e3b51f71ccd","9560011a-c647-4374-8439-6a5003e5de6c","2cc5c8ce-7b2a-4411-ba42-00cdb875492b","4ca89f72-903a-4869-86d7-539782dce83d","447670b3-ec07-4547-860b-d7c7f0dca58c","6469d701-05e9-405f-88a0-1771a98f37f6","70e2f653-f2b2-4035-afb6-2c3074efefc6","6e86ed8f-7d57-4a04-b560-be33dfee669d","d302b8e9-5819-43e2-bf08-12492b0d4b9c","fb68753f-9cae-43ee-a7a5-0f656b56f9a1","08e2ecae-b5a7-4382-9a2d-f3cd1b2d73f6","f255eeaf-3706-48f2-8b10-4b0c2a36e3d3","d9a9842c-5138-40bf-989e-57cbfb6eb4c9","7feea500-7108-4a1b-950b-ed52c255a6ae","5ea28a28-8292-43ec-8ac0-f4dd29c71b53","283f0491-4b04-412a-b0dc-a5ca9faa851a","02d8ef76-be10-4116-a15a-b984a5ad2465","f844d59a-0570-4c02-88bc-b4bb7af086d8","58009d58-2cab-445a-9841-f4b8bb822e4f","9ae1505c-aec7-46a4-b759-de7d14670378","8ec433ab-f484-4f02-ae79-1322dd69fd37","455313ee-202e-419f-b4a4-a7e402856ef4","3f7df2b3-5ae2-4910-93de-ad4aa9c139b1","56e4cad4-d571-4070-be42-141d648c0b44","9e4dbd46-e1a4-446a-895b-b09e31a69e1f","00538cf0-9c36-4428-bbfc-0c8338d48dc7","b1a2a677-7df2-4a89-8216-4bfe014324cb","725d8ec7-6c97-437a-bbec-67f6cae1c19e","132d588d-3b77-4a83-847b-638ac9195c44","5f287d2b-451d-4a15-bffc-bc95abd415c2","37e0466c-216f-498b-a387-15731754d346","c7612583-527f-44c7-8844-fdce6f4e52c4","2421b679-8efd-400a-8b0f-95bb04cb89d0","30da25a0-ade0-46d0-af73-92eebd95e118","57f4a72b-0821-4310-8f66-b3ae5314bf7e","5f5ea181-09fe-404b-b813-31b879c3f205","5f5fe327-c689-4a0b-b31f-5b515843bfbd","ac522861-f107-49ea-aa91-010bc94f0ec3","ca99824b-4d68-47e8-85ce-bbfeb63fa1e3","deb2a35d-ef80-4add-a608-cf6aea3b8d0b","ed5a4b92-82c8-4d86-a99c-5bc305fedd22","dd3c7932-2633-41be-a9cc-1de5b450da1b","49f61bf3-c923-4577-be33-dd6b6480724e","60b5cd5b-7cc5-412f-9dad-2bdeb709fb70","b04a3d1c-b656-4533-9933-82c06bf2e18e","f7a4055d-0125-462e-8124-7af10a7a9101","d71d7536-9cfa-46fd-83e2-3f89d2ec6800","abfcb3ae-55ee-4028-8ce6-dbed2ea62fdc","16cf9313-c384-4615-952b-96f97158bb53","23ee4926-88f4-4049-90fd-9765d5151a8d","4fd2c9ff-3251-4b4f-8fec-bc6934cbe152","681da44d-4e0c-470e-894c-cfe95ea0acf5","8a4a50e1-02a0-4e31-b5bc-2f2bd3ab1387","ab0dd344-cd5f-4a89-848a-d5350a4258d0","b7c44e96-d85a-47df-9eec-52afd369a45c","ccc99c08-6bb7-4c36-b327-78155a56bc7b","ec456a2f-443a-49de-8265-eacd86b966e9","f87f6372-9f25-49f8-a05d-d3ee57d32a76","374fc9dd-efa2-42c8-8066-1abb6de31e60","5c594513-b706-4b5f-9ac9-463538c26a3d","912171b4-b21a-4fa4-b3b0-003f92bd10da","d5ae960f-cc00-4d05-bd44-5afdd9d7fe3d","06409c4c-9c84-4162-bed1-113198207327","7b453e9a-f9c8-4b90-9270-2d5b3fcde691","4bab3441-33d2-4b4b-8f95-1604609a9787","b0094c30-d2a2-4722-9918-284e9696963d","cbd61451-cf1a-46a7-a545-e9d552e08cbe","59c86050-67e2-48c2-a74e-4515217dadca","2273074b-9bde-4fe5-8f0d-2447a69d91dd","d405c5eb-43e6-4187-863b-fefc3b9db4dd","118595ae-83f9-43ed-a94c-0de106de6c37","ffc7cf0e-5c3a-461e-9e94-3b385a515557","589b2f17-ed64-4ce1-b4d3-2cfd6f8ba851","5dd60cb2-c705-4730-8874-8ac5f5981d17","4ba8d736-5973-408c-8cfa-727479bbf2fe","6ba15390-aa7e-4d54-961b-cc35d441a761","26935bea-ebd6-4e91-b75f-977c853d2987","392aa9cd-195e-42a2-a55c-be5e2fd2148b","18b6eae4-d5d3-486c-9dbb-45f266bc9b3c","ec7d1e88-e43b-41bc-8f67-0a96864a0363","9f4045a7-900f-4f1d-8fe5-80cb3ce8e394","81d2c2a5-c0e2-4458-b538-06a095936682","da98aaa8-205d-43d1-8283-aa7722ec1dbf","113ef64d-f795-401c-9b19-f07d87a1c5c0","5e609318-d290-45fc-8492-b25848d35a32","bdc961a4-f883-4652-a8ff-e4b4ab199e32","38d31150-c262-4115-ae46-f1217573dfeb","d1bf3cf7-3209-430b-9f88-1daadcecd40e","f54a9303-0c85-4b5b-808d-8686b3e96d2a","6cb8637f-c7d0-404b-8539-ad7cdf64bea1","8040be22-dcb4-47c4-8ceb-e0ecba595ffc","90926023-c9c0-4d7c-a3a8-a52af396a469","90f976b7-c835-4696-86c2-8accfb09f4a3","97047608-b5b5-4c7d-b6e6-4eb377d524b3","6f492a43-347a-4687-90a5-d84ebcf404a8","445e7261-786c-4874-aefa-e71fd722f856","31d22c51-2686-47dc-8769-60f6c1ff82ae","77613095-b910-425b-88a8-83a3b8438720","525153ab-750b-4924-8d79-fab0491d62de","bbe29e24-029b-4c63-9a09-5d9cd5985e97","ac93e583-60ae-47fa-a75b-2e61b1ae43c3","242215b9-0334-441b-9a77-c439d5e54d2d","bb4e6ea0-252c-40ce-9650-689a57134ff2","f67a9ce3-5019-4588-b882-1cde1134b27b","5336556b-601d-4a2f-8e47-4d3e35b884f5","df40576f-6074-45b9-8a4e-91d91a478d78","f4205cf4-502d-4479-b5e2-561824ce48f9","b3ca1044-e127-4b7d-8852-b852640efac0","425daf33-8edf-4243-a142-6f0190ea7fe9","7854c567-54d3-4f45-909a-9087f5313d0a","139c0d1e-a731-41a3-af41-92a7296bdb34","bab66817-9570-4537-a201-250e0cbb1daf","adae567d-9980-4677-a767-0db995b9a5c9","0e45c94a-017e-4a82-8f90-1733af3d22d4","380d4eb0-8e6e-4459-b7fe-d1d3fcec0336","69efcf10-a9da-4537-a99c-aae1e5b2f8d8","ff4cedd5-5f49-4aa2-b87b-27ed160cd6d1","5f48b8bc-e040-4afe-b271-536ab52e85b0","32456cea-a541-42b8-bcfe-f6e9beb03986","6339eb5a-f008-4511-90b1-64837f45a0f2","3503d4a3-2bcb-4c24-9c8e-b2ca3757d9c1","10398d42-5b5f-4df8-a402-45ac5355fb18","309f223c-d89b-4e16-a849-c9677c501dd3","c0fcb8e3-ef84-4093-9d06-72d57d0153ef","30067924-9650-44fb-aec7-feec071e24ed","7271a0f8-acc9-4e44-bd3a-27d45fcbe684","162a4ac7-8246-4726-946c-cb67ad241de8","97eea658-d320-40f1-b5d4-92fc462447ac","8deec02d-b07c-4e44-8ed4-a33bb4240cbd","d2022950-d350-4dc1-bfbd-f3e872830448","c70888fe-012d-439a-96a0-a7a20259d8c5","7bc110da-dc87-4b7f-9895-61257391c80a","3f9aa084-ddc3-480e-96ce-c7f3dd81f633","ae98cb08-5c35-4057-9ed4-41dc7c969067","04eb4318-4684-43b9-8aba-805ddb4cd850","87ce6aaf-6503-4eab-9a2a-ff368d1aeb7c","11c7d75c-09a0-4038-ad55-8708d8aeee38","b409e214-6266-44cb-8891-e62e98fd7b85","896cffb0-09e2-4e4a-9e8e-f37f4db9f3ce","0f4fcf62-0266-43a1-b6c9-1391bf13650f","94802ad9-6ccb-4bde-809d-062f8c814135","8d5d2a52-57e8-4712-850c-cf952277ebec","1569fab2-6ed0-4d83-88e4-d33699370a96","3897718c-0edd-4946-832a-76f20db7fb3a","a28cd24a-0f88-4c78-938d-714fb329b24a","c4524320-673d-4a5b-8cd5-f25253da7d0e","809143c1-8389-4404-a532-5e8ac6dc781d","1bd56c91-5a5e-4191-a111-935e81b24d0a","6a7e5e57-524c-4b97-94a9-16fba679b3bb","4d037836-8f84-4b29-b504-02fcdfe8fbc6","6955a7f5-067e-4094-8314-365578939fdc","e342241f-fbbd-483c-8395-d386b69266c2","80081976-a63e-44bf-9660-e9e296373950","17232cc4-618f-4583-8976-75480077167e","9b72b4cb-bb82-4043-b002-386d153841d3","d3a3fba8-376e-42f3-accc-1205e5d3ac50","bdaea74d-3321-4b28-a0ac-0f53f40a81cd","40af7fb0-9e76-4a1f-ac41-6cd488159284","71a5a5f4-6177-4dfd-bd93-ef015b9556dd","eda4c05e-1fb5-4119-8729-79ed688df7dc","2933346c-271c-47d4-8ada-04a0b3c194a5","ef2273dd-c6ef-448a-a8c3-da6e6ef91530","4ed17802-7b76-4480-ad38-a83188b12456","27411a1b-738d-490e-935d-604ce509e2df","94fa6188-7bd4-40ec-8b2d-6e627d92c926","92e5cbb9-e45f-4db8-8c36-27b6e7493de6","01fe8150-8abe-4a5c-bb11-c23afe0be5f3","592f8133-5fa0-414e-a3ad-7cadbddc1bfd","12eef4dc-46d5-4751-a09f-6aec5f7f1d74","776596bf-bbea-4445-9770-18ee68205326","98ec4d53-88f6-481e-a639-c0fe6031bede","f813bb72-f85e-4377-83ac-d83018a625bb","12c56877-396f-4f32-99ce-e35f2d23356d","1c5838d8-0294-4713-b63e-c47d0b2059f3","180bd8fe-db7f-49cb-bb36-89ace50110ed","0429181b-a737-4c13-ae68-61790e8dabf2","fc456533-2610-4d3a-bc10-1bef27531204","f82166be-b866-4cee-ab80-631ce3e4301d","110ab1c5-eaf6-4e85-b4a0-70d5b939358b","15ad39a1-c356-43cb-a017-4c03c1bc2526","48a54a27-d459-49b0-9f8a-83f65ff6b736","3895b25f-4d6c-4949-b72f-5ea55abbca82","02e6a15b-dab5-484f-aab0-b2912cd5a1df"};
        //专业职能类
//        String [] ids = {"d9c6317a-7190-4ce0-ae22-c7c7b98114dc","b86784e6-118a-4665-9de1-2bfdaf08aed8","e26c9998-7639-4f1f-a673-a13a6d0e4b51","c52a6335-b65d-456f-9270-e4ef69dc4810","11e8b3a1-f9f6-40fa-aca5-01051a779915","214cfcd7-3b53-4c93-8792-19dcc1027ee3","3e257677-e194-4c75-985a-c3c37a310cca","284b3f8a-aef9-4c04-9424-7d1ac83c2d12","4fe7821d-c988-4142-a5d8-97a8a20acd7c","6508c4db-5ba3-4d95-b0b4-4f8f3cc2aded","77055acf-907a-407a-a626-67987fc4c012","78193652-7e03-4aba-9d5f-eb8aac98471e","5290085c-ff21-4a2c-bd63-b6b85e0b827c","97f597e1-9b3a-4564-ae2c-fd8594e3ec48","c38c8d7d-c25f-4798-9705-dcca3318ed43","019774f7-3473-40b4-9fa1-9b22a19a363a","983f0901-c835-4384-96f0-659e3f7b62b4","5eb15c2d-7def-436a-9056-47a4e0a8b2f6","34c44135-92ea-4a62-8fd5-5c49de68c5a8","44ec2b43-2e40-43db-bdea-194b7328fdc8","e6e48aef-cc66-4089-b9e8-9699d74167e4","7368243d-7b31-4d42-a836-e8a00a97fa73","b2fe6159-a1c7-421a-9950-261330db5d55","6643911f-9089-4798-a6cb-ac24ceb3a414","733cff9e-f382-4e2b-9e73-b237963d6f79","cef1e3e1-c8cd-4725-85bc-fcc86e57d1c4","2ba40fec-0dfe-48e6-a181-ee550a170cc2","90576cb9-2452-4e06-8ed6-9c87855b6f75","ba27855c-c504-4b84-98fd-ecbda8c05b4b","c6ce1a78-56fe-42b5-bda8-7d64f84e8396","e6869dde-0564-480f-aaad-f6662dec5973","6c3cc6ad-4ed1-4d42-b7af-4891cfd868b5","26f79e89-9059-402a-9d1e-7c5fb227f7b9","f7932718-11e8-42e0-91e8-1f41cd4e5ec7","a86e9d8a-1320-43fe-b95a-3ac33d7f6d68","b036512d-9995-43ea-b464-39e2246fc42a","f8deb376-6f54-4859-a325-390d0d4beb32","7a264bb0-3a6e-4bae-9eac-5cde3cd92fbf","4013e93c-4dcc-4ed0-b8a1-1af30d298231","cdbcc177-9b19-4c72-99f9-f8d1ed85ef83","ad7d463a-4717-4aa0-a56a-59ce639722c1","cdff85e0-0bc0-431a-8c3e-09dc7451f07e","3f76e3b7-5544-494a-b31e-253554c19950","4aea903f-16a2-4224-8ead-b14539ee82ff","3d463741-c983-48e4-ab60-2fc8e5e7f466","0d407c72-ba27-4649-8cee-b98679c7c0fe","a875f151-ec7d-471c-af46-c141ebed3531","be6a2445-fcfd-4311-85e6-87dd190f2e68","2b0649de-fba4-4741-93cd-c5c92c7f079f","101438d3-9640-494e-973c-f0bea0177f8b","4a6001f7-f9bb-4ae8-8947-4f50790d5262","95f1df11-a0b5-4cfe-8c92-6bef317d2fca","26aa8619-36e0-4ae5-b83b-5bfd95aee1c0","87858ce7-dee0-421c-9bdb-5b05cedf2a0d","188338e4-d692-4259-a834-46996664e213","8b7f3ab6-7a4c-4197-b28d-944e4bdbc373","dd799db1-090e-4e35-8d40-6a31331e0f69","2d42dc1c-40c3-42d6-b893-4cf69ad1de63","5e3d40f7-1721-45b8-a3e4-d6cb31a643a8","95bbca40-1a0a-43b6-b73f-e1cf6725a77e","a41e5a95-222e-4584-9fe3-bbfb3126a151","5c5412bf-703d-4e08-8fd4-59d84eb06a5b","bc5d4e40-cf18-401e-b5af-cccd37de8550","1a12840c-cca7-45f8-b2e2-485cc2774b86","ca746796-5030-4410-8a2f-362602d72ed8","44541e29-964b-45eb-a57f-6d15fd73be45","1b739eff-14a9-4798-81f2-a8e8363e264f","853b7531-eda4-463f-ac4e-5ac26355046d","871aa099-ada6-4192-83b4-7b5a37ebe1fe","c1dafacf-4d48-4065-8cc7-22786d77e1da","13ee118c-5b7a-447d-b442-e4832fe23b26","220cf140-b45d-4149-aa1d-256d9d17cf6e","45e7b2a4-0cfa-4f76-82cd-7534dd63f7a4","ba7f7036-262b-40d1-b33f-c82a207d880e","df6687bf-10d9-436e-8b65-709c70f08dc5","2fd50d96-7e10-4d5b-9383-f933c0470ec3","72f11583-302d-44c2-bdcc-77a45d15cdc3","bd855381-a86d-4e1c-9ef7-8f608f786775","732edf5f-9130-415f-992b-3829e555bd51","72bb0098-ea1d-42d0-8c94-beaef25e1221","7b28ad5a-3ea0-450e-8a66-5045e165ed50","2abd8782-9f95-4a8a-85af-c015a68229bd","ab970655-c215-41f4-8cc7-3454695010f6","5e56b479-d17b-4186-ba6e-6deadfcb8912","2252e2ed-9a65-4745-90dd-f6691072f20e","5eee5716-2643-423e-acb8-28d6b93e289c","2458d242-58e1-4991-8d91-95c501e3bbcd","715c16d0-db23-4a43-9c01-c5064f1f8406","4d76b59e-6f3e-4fb9-9f1a-d55cce1724f1","8ce2594d-5436-4654-9caa-68ec63e9d5dc","e0e516bd-62e0-4cc1-a726-b4cc6e1ce6f7","7551b234-c681-45a4-b80d-0e2280b555f4","eafce7f9-3a72-457f-8af8-324bf9c5b7d3","8753b8e7-fcd1-4dff-b5ec-091aa8e929bb","f1f2e791-3a83-4856-9a98-cde41b543f1e","6f29ceb7-bfe2-4dc4-b3df-b95f4586d6e8","20d3e7b9-5aeb-4c71-8a68-69b01fedea52","52d5ff0c-082c-4e05-b1af-5b81c5c2bd9b","172b3ac9-af7a-4f97-a1af-93e4dd5d0f7a","c7927895-75ad-4008-9140-36c00d879cad","d86ccb44-384b-4e8c-887b-ff26de684ffd","c82c35a1-2024-4317-8db0-822a2700222a","f66971b4-f384-480d-aa90-d2186156162d","3741496a-ee7c-4823-afa2-5d46588219ec","eb31bdb8-e24b-4679-be9d-6a9fc56ea6d6","dae53acd-c2fb-44f4-b474-9dcdeacedb7d","71676f21-5aa6-47bd-a9a1-79c7c358264d","ca8b21d5-3d08-45fe-8be2-e1eb8771763a","3d130234-3de3-45be-b622-06e2327dd7b5","026d78c5-6d03-4387-b408-4f3ff1cae83d","aa12425d-7884-42ba-bae2-c58c65aa7870","04c4a4b3-dcf5-437e-9dc2-9ddc0b12636b","d3ef0af9-8b5e-4e52-b95d-3574d11644f4","b18a2449-be6f-43ee-9749-f0d8b7de890a","73f122fb-5b15-4330-a8a8-1d901ec16ad6","f788c746-af7f-4aff-9b78-40b0f4b01896","3d24fdb1-fb0c-4ef9-9c96-85cb64451b50","c6f61b06-7a74-4463-b5ab-64ab52fce80b","92320f37-fac3-4f79-828b-1da66ccf3a8d","fdab22ac-ebcf-4bf3-8768-cb9300c67f44","14a25c24-4849-45d8-b1a6-d38b2dc1227a","2918ccaa-3567-4b4e-8988-ce90dccd3134","19da788d-8e5d-4953-9cf4-d5cd0f0c0646","47987ade-9a2f-4a6c-8359-93c6be262072","e034b6b8-42e2-402a-bfc8-65a689b79580","0961b9d1-feb9-4a39-bb16-593be47af6b1","70618dd3-387b-4f62-a5d5-ffafeb2d4c2a","a2d9cbf6-7624-449e-8902-c538bdef1c0a","3f4eb305-d36b-4a5c-8397-a46ab39e0150","23c38ab5-91ac-4bc7-9d34-162106e4d696","7bc8fb79-5520-4fe2-989d-dd98fd2a9fc1","f6e29260-19c4-4f18-95df-9418e3708129","5b2a703b-dac6-4487-9f60-5c2852a2273d","156192ac-050d-485b-98b9-087e56a2bf87","6c16e296-8e1c-446c-b11c-bd408d4fb088","224e699b-832f-4c4b-9fff-e668fa7bdf28","c629ebcc-09ae-44c4-91a2-1321db6f0173","9d0ed01b-ba03-4d3c-9fe3-1c0cf43ac5cc","b422d556-eb04-499f-9a22-5e1d31620095","83822d97-108a-435a-8147-8c03f8b24b40","e9ddc6b3-4a95-486b-9d7d-372c5d7fffe4","627a06be-8fdf-4101-b3be-30d7983a3c84","ea8356fd-31d0-40d6-a07f-eda835b2afe6","433f9e52-6caf-4a39-81bd-ab3fd13be71a","5e112857-9eee-4cef-b4d1-2edc91fb7e5d","3cd5b89c-6743-4ba0-b6cf-b192ab5dc219","6ab95c11-1914-430b-b1f9-774a410f296a","01308d73-cc39-4c07-8a35-a2803a74f05b","fa417567-f1e6-4ef3-bf82-1e474eaa12c3","5a3ac291-ea99-4098-af10-06c3add09720","a36d5041-b831-48e7-b263-3be0853cdf0f","2a8b6983-d411-42ac-bbd9-4e977ddf9fcf","583248a6-a353-4d20-8eda-e58c8e15da53","cb67930e-955c-48a9-a8fa-b9353b4cebee","97d55b2a-ec59-4235-b595-4ae94baa32f5","ee9c7ed1-3065-4b91-8c7b-c335822f3265","93197d0d-b814-4e46-b8c2-73051a070a10","2bc23016-2bd3-4bfb-bd12-124b94589aa0","c199bbf9-d8a6-4a6b-a54c-ea20d3dd9991","a6a8bf68-2ef8-4b51-89ef-fa76df4d8c80","aa936254-eb27-432c-b115-e1bf0935ac8c","3f3e530a-106b-4776-ad71-bcc0a38ae453","ff131274-3e39-43f7-870a-239ab72b5405","bbc1afc2-a061-4103-a695-6979a6357ecf","6a3c66ee-e56f-4a30-89d7-1f188db5c773","2f597b85-22c3-4f71-a5c5-a050244c4bab","73cb88c9-ad7d-4fd5-bf60-8c9710e72e29","f71e4ad6-e582-44a5-87ca-7e9ab95d5105","533ac658-24d6-4a22-9aff-7504ead2ebf0","cb3eb472-e397-4605-850b-a62c2b412d87","e6c650b2-6da5-4c64-bd5f-f29c72a5e3e3","8d7dc3e2-7436-46fc-8882-1cf145eb98cc","b9bda7af-a013-4839-81d2-3898c70e79f8","65a8c495-32c2-4cf1-85d1-65887ac2e83d","0217d51c-3ac2-4569-bd45-351cc4a24ce9","ff6eec72-6c2e-4853-8707-5119f1cd8ff2","22e54765-fcd6-4ece-87b2-cc6c5c89c47d","4b517f57-5279-4e2e-a5f1-fe5e15a791e7","8a9786f9-3a82-44d4-93bc-db766b374cc6","7d40a82b-3a3a-48a0-ab00-c989ee9d24b1","cbadcd89-e072-4c37-a5da-a441f097f970","b8b4f55f-6992-4bf8-a9c2-edfbf1a64537","9b5b97bd-6cdf-4442-ae5b-95323f4363e6","3ab86028-7d1b-488f-9e53-8fb00f453fa9","81893403-870b-46fe-8f48-3bcef57174d5","e8b0dc86-a7b8-46a0-b45e-87c94fee0959","91bc28ad-ee60-47f3-8667-3590389ff153","758de476-8088-4bb4-98e0-57cbc3ff8153","94c98c9b-c558-49c2-8340-b1cb6019e71f","2ac2a7ce-0a51-4296-9060-2a8abdb3976e","0df9c4a9-034b-4bd3-8ab0-32d23dac0977","72755a4a-13c8-4b81-a69b-b5c91e33772b","155952c4-52ad-4ad8-a1bf-3777171cb71c","f8d343f3-d435-4e36-a1dc-5618099e62d3","fc792b51-2f2c-40d6-bfb5-299db3521b67","94e46dbb-ee96-439e-88cd-b912ec3672bc","08a8136e-dde0-4e26-b00a-9bb1fa4806ab","46af9077-9187-4176-923c-4fed39023c3e","e49d494f-1958-43d1-a9f1-ed3659098608","e00138a9-c4ff-422b-a099-fd6512fff30c","2ed79472-5219-4337-9bf5-63ba58b11093","72e8b32a-87fc-41f6-81e4-12a7d5e8fe5d","024fe339-bb20-467c-afc5-1051512250de","1ec2abae-fab6-456c-9027-f987bdd08b2e","4b55ad5f-d277-4e32-a017-cf7f17c2c312","1c762556-c407-4d27-8340-04d214efaeb6","3439ee65-a486-4727-bcc2-2a1a668791fe","bdbcf88d-1c99-4f3a-b3b1-a5e4a1811a03","4e2f5598-ca95-4039-b349-2999a561e8c9","35051b86-f6ad-4693-bd1f-b69267c49433","e4ad51f8-1b2f-461e-8dab-fa20b3b1d8ea","d423ff96-a11e-4353-a5c9-09e5a4dd3488","5853a26e-7976-42e4-a74a-7ff36205bcc2","49628eab-e27d-4b65-aac3-33e5856c2240","6ee6848d-f314-489c-b0b8-18f9eb247a84","e014b525-bab4-43a6-87cb-7b0fd4d32d77","8e9161f9-5709-4197-b106-a93d11b16f48","3d26a718-baec-408e-8192-b305d13bfa92","cfb77f4f-2279-48d3-a98a-2dabb4094ded","44489751-dd11-4ca5-842f-8f47f7484ddc","586e188b-7d9e-4565-be2c-3fa1d66aa674"};
        //职业化类
//        String [] ids = {"602ddc33-7243-47ee-a427-738907dddf47","8e03a42a-7993-48ff-a4b1-da64bbc8cddb","c68cd8f4-bc7b-4772-894d-131d5930af14","504494f8-a7e3-469b-acfe-179a792436fb","e5ac1e16-a0a7-4c08-978f-2fd261c327a2","04aaf0b8-f795-4ee4-8e4b-7fb8a10d6b7d","fed33046-34bf-42de-85e0-0c3c7bd808c3","0fd7da12-7d54-441a-88a8-75cff81c6d8c","0d877e43-ed7f-4c71-91d7-b3685cd4188e","10c93a17-003f-4448-b48d-aa17a6938c4f","a0139493-eea8-4d2f-9310-dc5e581c12fa","4c0c2096-ce6b-4750-bb46-38fd582ad9c4","1abde4f1-b775-400c-928a-0b514a544a09","61c168de-0117-47c5-a5fc-ed798709805f","60ae5a4a-d6ce-4a24-86f2-3475d74d61f5","30a735ed-171b-4a8c-813c-b14442dabbed","de28a100-75bf-44cc-882f-9085cb1a5643","4b10c4c0-1322-425a-8122-f22f744a6a23","ce9a0172-de2e-40c2-93ef-c5b1ce8533ec","b02a12d2-25c0-4e4b-9c12-f32ca3a992ce","b7177570-36b5-44d6-9466-563c1451a4b0","f81b00a3-c3ab-4559-a2c5-b6905cdeba48","92dc26a1-e013-4d77-a617-5eb2712f65cc","5db48689-e5c2-419f-989a-33aee973d111","4888588e-bb69-45c2-a836-c36e7d32993c","b2d3fe27-0801-4c06-8632-a4787552aa3c","0c3cbf97-18a2-486d-a2b0-3e883e92e423","f308b707-cd4a-4ef6-8663-e13c87836df7","4625e120-5d88-4233-a577-b3b3a0444298","c6c173e6-e32c-493b-a3c8-cbe074385e05","b56c24bd-dc92-496a-be38-5985c3116f07","1306df30-c881-483f-991c-622566587e6c","449e09e2-1617-4ce6-bad3-dab85b673e68","fe9346ab-8143-432a-ab24-b74ea424afd0","a88c79c2-7897-4f98-8693-ebb7d393792f","b398066d-e57e-41c4-b05f-1d64140fb5e5","d44e4ed9-b0a0-4666-8387-d8b2d664ab2a","5f2d5294-eb80-4c1e-a3cc-597995736b78","d137c167-10b8-4498-976e-8abfcf0e3a7a","e3802466-8c94-487e-8fae-a9ae98aaf309","e6263fb3-21c1-404d-8e58-8b656b4bd98a","8ee68f5d-1f3c-49fa-aba6-180955a88057","5bed6e7c-aa42-423d-8e92-a6bccc7076cd","34075d07-18a5-45d4-8aa2-d1f3afca68f3","28671b51-7654-4b34-b385-34f50da47023","b9bc5703-c092-4168-bddd-650ea456f78b","8d5a8daa-d828-4fc4-a1ff-98cc0d6a282b","72a1a002-8457-4a9a-8a92-6a8c7947c159","b74c359b-c160-4da1-9d24-111bc9908847","fb044e92-da3f-4bf7-8691-87eb27e4444b","e7e0fa5b-1817-4c56-b8f4-8023bc185b30","45d1d778-e98d-41ad-8883-7ed84392a603","e7347356-cdfd-42fa-91cb-c1671c0f1dd5","162e6477-8388-4070-bee5-f203dbd7e589","138c5e7f-724a-4461-9774-ec536d6b4d70","e5783c82-938a-427c-9617-2da0b4704518","9515a7f6-f338-4456-a10e-a0b5637c03ab","627faa2c-d44a-41de-8daa-e32708b763f4","9289aa7a-ce28-40b7-8255-c02cf7cd0b22","08ae639a-45cf-49ea-8eb8-51a7d1bd28ef","581cf42a-2843-4ded-8d51-47bbc3acb969","a0c8aee9-db96-401a-b084-4b248aeb9349","a4b6041d-481f-4531-ac1d-e675a4bde41b","92b165b7-0d94-4559-b8b7-c6712bd7f124","83f4438a-08ac-4f51-9ed9-f59bc375673e","26cc8c13-1820-423a-b78f-e8133980fab2","b48eb0ae-bdb8-4029-9d7d-7331cf158f96","dc233e63-da9b-4d64-bbfa-c05d3ba9155b","987375f4-5d67-42b0-8af6-06a0d570fa59","0c9d61e3-4c8f-4c1b-88bf-eadbd1f87b6f","e95ea74d-5833-44b2-9451-66c7306ea3fc","e2347b9a-efc5-4748-af7d-5528f1982f38","235b87c6-b0a4-4669-97e3-473bd4b28723","d152a6fc-b056-4ed1-bc6f-c7a65ea88d51","893fa4b8-6100-4897-a4a9-4a483870d19f","7ec5685c-c84b-4802-863c-99d1071b6382","ca6294e4-00c4-43b3-8a29-4ff3e38bdc6b","648addb6-72a7-4440-b01a-c36479909ff3","e0dffc67-e3b3-4be1-bd7e-120177a9d39c","11a94cb8-a016-4f49-974d-bed45a015c1d","81a024aa-ba94-46a6-90e9-ac356d9cb289","6afa1453-da95-43ae-869d-e28e19afadff","4709063a-2b26-4492-bc1b-83dc58b06260","3f8796d1-4dad-4ac7-b534-a4e6e8e77249","47304ef7-cd43-47fe-8120-2fbe71533067","ed642e0b-c731-4d02-94ef-f4d0ed269f97","417107b8-3991-463b-99a6-b7ffe31eeb59","31128de2-78ee-4b40-9c20-f02d4a4a8fe6","ec816a0c-296b-450b-a6ff-20ca8a613ea0","dd30ec6d-5e9b-4386-bc25-4aee093cedd4","fef39886-4fc8-4463-9c1e-fc45e69a04a6","bf23ecf1-05b8-4414-916d-c6e3b8e7ca87","0adbf81f-c80a-4b33-b655-bfaf3a47ac39","930296a6-bacc-42d7-98f7-fd89a8f952b5","cce89f6e-450d-4965-b21e-bb5c08f90673","0c225a29-6da4-48e9-9f5f-c1b32eb373dd","b7ad2114-65aa-45da-879f-7300cc23cf1b","f9a64b7e-24e8-4a6b-b4b1-a2c130d25b92","af2c593b-ec0e-4cf1-922d-6616759eaa8b","84aba423-2ddf-4aee-81da-0be6595fc893","e3c7311c-81be-488a-848f-2de9cbffe9cc","758d4719-208c-4dc2-a720-de6131a2230d","2fc19111-1b5f-4952-bffd-c474317f56a2","701d296f-f2da-4894-bf18-3a3754de2ba1","243ff472-da98-4521-b037-8b54a540b69b","4d36dde1-cb38-4060-a5c2-4223c2eff698","613e112c-9cb1-4104-9fe2-9723005aac25","61b10efd-95d7-4a67-8ad7-881d466eff86","8bdc7915-2e3d-4f2d-80e7-53f9d5829b02","c2cd8fa1-0d11-497d-ba1e-300120bb3674","e1d7c3c4-2f91-451b-9a05-15c9e7ff56ae","e286337e-0b31-4a94-9d9f-e38abfb923d8","e646b542-c13d-4be6-b9bc-e31d0b8cb2a4","4afdd8a8-905b-4ff2-aee4-ff3d642f498f","1d7d3ec3-ff53-4909-866d-56af091d77b5","edb559b3-a28c-47fd-87d6-d9137b1f12ea","b4e6576b-3bef-4659-9630-86cfc7428d03","383a9eda-132f-4386-8844-557928fee7db","47901d41-0a3b-455d-a10a-421ce46aa49f","3698ad58-ebbf-4aec-8bec-484fa4ca7528","4c1b24fc-0e70-412c-99fe-28d7bacafd8e","6a1ad30d-2e5b-43d3-916b-b7098f5c73fb","3386fb95-6780-4483-9958-af483e75b95e","d65cc43b-d20b-4208-bd17-3063ea3a3b68","94247625-b878-41be-953f-79ac6c0fb260","21fd77b6-3243-4579-9226-4db06b302188","b24321fa-9700-4a87-bf78-ee9737806ae8","1ce2fb1c-62cc-480c-bc12-1f298f77b877","a31c37e7-6696-45c4-aa1b-329b0585a178","b6bf5cf4-b2f8-42b7-86d6-823fd3fe7d46","66128b87-0424-4408-9d29-cfce5c047385","4abead2f-fbc6-4ea2-be18-133976c5f910","aea21a36-92c3-4d4a-a6ca-17518fccdf17","5d487d63-90c8-4304-a89b-b506c3a4675a","883bf73e-6085-4392-ad74-343b47923a5f","47237740-68f3-45d4-9446-cd0c92809a55","d1bef0ae-039c-406e-a732-6e4da2218053","b39a8802-f4f1-4744-8f86-96f878876041","46ed6b82-cfcc-4461-ab5b-6402cbde40db","813ef774-85c6-4607-b7ed-1e1183a3fedc","cf6a3117-c894-425f-9f58-ef62d05441f2","777cce62-a25c-42fe-a4c6-d5469a826837","1f4090dd-9cff-499f-816c-69dc8143912f","a557625c-c9d5-4cd2-9369-9b547b5a9613","84d5f153-f0cb-410d-b625-c72e1064dd52","7abb0389-501c-4bfa-b526-c4b9660b72c8","17919492-4ed7-4fb6-991a-930482ff9a08","bb27c27a-90ba-4ef4-9a59-2d83e410c233","d3de50b0-529a-48fd-8d93-5ad9a3f47120","f6c211ac-6c1e-4da2-89fc-1efc01bd077e","f0e583ce-4d87-44e0-aba0-5fb6ef7055bc","1c96f767-fedf-4b3e-b412-7165a9a8644e","ee5abac8-d670-48a0-b4a5-9f84ecc49513","a0eed30d-74cf-405e-8afa-6b855c590fb9","ad397212-e23f-4ded-bdf5-4789fca1d5af","48f75188-0dbb-46ef-983f-1182121bf0ae","57d2cd96-9fbd-4154-ada3-219d91943617","74f9cc31-641c-4d6e-82a3-cf44777334c5","4f3c1bfe-4407-440b-8a7c-5ecb772a4be2","a9ec1748-620f-4f3e-8975-46bc1affffd7","26fbe4e9-f2bd-4298-a96b-4ea51500c189","568212ba-d960-4325-a253-b2bdab85a917","d3045df0-d657-4bb1-977c-b4d1df6b26ee","cbe86ab5-2abe-4804-bb35-bb374ac91661","6cfb0134-d332-4f4f-b0af-a16a93f38da7","1587b879-d6a4-4686-8970-6f52ee21377c","f8fc8d4c-9959-4602-bd1f-fbe6bef9c0fe","a6863c15-6422-48b2-9361-611a472e3a1d","d8840145-eb51-49e7-a75c-7866e4f67bdb","08d8f1b0-cb40-4a87-8a74-41148ca4f414","6ab29a93-4ada-44cd-b9c8-24dde113888f","f2ea2ab7-4d3e-40c9-92bc-9b40dd68c565","357d98b1-fae5-4b92-809a-c9dfb9513000","e4b4ec81-ac4b-49cd-afb9-acc418dbff9a","413a6f44-5b91-4f37-801a-a78f64f537ce","618928bb-7e57-444c-9a8e-cc8ab58658d1","f6407782-d0de-496d-96c7-e609e6faddc4","de189c52-06f4-46fa-ba4e-da36c605c3c6","d376f6df-0469-4b03-a11f-7d299e85f4b6","bad78ce0-9858-41c0-87ea-85eb060f0278","e63a35a5-6088-42cc-bce0-f3ba4ba3026f","4dccc4d5-cf60-4022-9b9a-7737e080473e","ed78c653-9497-4978-b6af-79618c6631f8","bc46b99a-1f0d-45ce-88a3-4fa210e9c331","f1ba4953-bd65-4b60-a289-5a29166cea78","8aeff8f5-d326-4d84-817d-bb67d52f8ce5","f93e1ee1-dd19-49a7-8542-299013550d09","5b4311e1-3cec-40ba-8740-998581b6b8ac","1bcfea81-fa4f-4eb6-ab1d-b3e3383b4a38","926832fe-43b2-4fbd-8ac0-4172d0167fbc","6c7c98bf-8fe2-4a73-9ad8-2cc334affbef","e722709c-1369-46b4-8e28-f3e1df1235fd","32dfab6d-78a8-471b-b31c-a27acce61b9f","e4208176-95ef-4c51-b92a-ffa8cc9d7207","d183f02e-c0b7-45be-9fd3-4a6dad31258d","0ee88c54-84b5-4674-92a2-4ee2fafd5f10","046fb455-f02d-4afc-a569-36127bae556b","3d0c4b8e-94ab-43e0-9a85-385a5b23b4a9","ef45d315-fe05-479c-91e9-49b372a71f90","27eff75a-5c2d-4fdb-9146-eb5b8b7a0018","87d5a0d1-df99-428a-a6c0-c805bdfb7b7d","a2255616-3728-4445-8525-9994eb33eb61","f04cff48-c951-48e0-aea4-0ab57c9f3057","71de32b0-441e-43b7-b2f3-f84a1919f889","2dd99821-393a-4380-93b6-e29b2284b380","89dcb243-0562-41db-9f9c-db1b4f4554fe","970f9ae9-438e-4e1f-ad76-b24096a69630"};
        //行业类
        String [] ids = {"9009220c-977f-4b6f-bca7-9149945767bd","a8e41f7b-bffb-4ff2-9224-5dc1b39bbb2a","17fe8586-4f4e-4333-9edc-2bdb4e44fc25","93837a5b-c801-4dc7-9e1c-f6f147960f62","149ed3b8-5e9f-442c-bfc4-c8a2ef87e524","b175308f-c3f7-4d43-b5c4-b01359e17bf3","bb4b2581-3b20-4a0c-9cfa-72001e8f2283","77165de2-9422-4d53-bd26-8c4ccc6e4c2e","764b2d5b-aece-4d1c-bf4d-b6d08cd568d7","6707f288-a241-495c-bc96-0e2212f3623c","65341f48-df0a-4217-9e7d-b1e7b5a4c65e","2f413a63-fce8-49fd-ad8e-d6dfca3dd0b9","878f1332-e97f-4563-8bec-9d8fb6cd0f51","57462196-9a0b-4154-b7f7-03d92bce1efc","a97e5d93-aef0-4a90-8e27-b5c863995ff0","9dc2e632-3b99-4aba-aa3e-78fde3108a43","175a179e-8397-4801-83ef-a029d2f52a52","4752c78a-773b-4070-ab6c-dca5edd60e12","9fdbdd62-14f9-4fc3-a801-d1c1a469940f","53b80b6e-b117-43cb-a187-1945d2424503",};
        //企业课程
//        String [] ids = {"f94476ec-9dca-4cae-a56b-01ffdb2bdcd6","8e7aef96-8919-4dc5-8d87-bade6511110f","c007fba6-98e3-454c-89c7-756bc02b60c0","f9587a6f-4a26-4152-aada-b3ecca82ba33","695cce68-29de-45da-914d-afd464424c4f","f2d3cf99-c283-4c9f-ba9d-517207633b90","e2347b9a-efc5-4748-af7d-5528f1982f38","602ddc33-7243-47ee-a427-738907dddf47","449e09e2-1617-4ce6-bad3-dab85b673e68","a88c79c2-7897-4f98-8693-ebb7d393792f","f81b00a3-c3ab-4559-a2c5-b6905cdeba48","60ae5a4a-d6ce-4a24-86f2-3475d74d61f5","fed33046-34bf-42de-85e0-0c3c7bd808c3","90f976b7-c835-4696-86c2-8accfb09f4a3","de28a100-75bf-44cc-882f-9085cb1a5643","08d8f1b0-cb40-4a87-8a74-41148ca4f414","b24321fa-9700-4a87-bf78-ee9737806ae8","fe9346ab-8143-432a-ab24-b74ea424afd0","f04cff48-c951-48e0-aea4-0ab57c9f3057","c6c173e6-e32c-493b-a3c8-cbe074385e05","92dc26a1-e013-4d77-a617-5eb2712f65cc","987375f4-5d67-42b0-8af6-06a0d570fa59","b6bf5cf4-b2f8-42b7-86d6-823fd3fe7d46","a31c37e7-6696-45c4-aa1b-329b0585a178","57f4a72b-0821-4310-8f66-b3ae5314bf7e","5f5fe327-c689-4a0b-b31f-5b515843bfbd","ed5a4b92-82c8-4d86-a99c-5bc305fedd22","5f5ea181-09fe-404b-b813-31b879c3f205","1306df30-c881-483f-991c-622566587e6c","f9a64b7e-24e8-4a6b-b4b1-a2c130d25b92","ed642e0b-c731-4d02-94ef-f4d0ed269f97","e1d7c3c4-2f91-451b-9a05-15c9e7ff56ae","b4e6576b-3bef-4659-9630-86cfc7428d03","417107b8-3991-463b-99a6-b7ffe31eeb59","3698ad58-ebbf-4aec-8bec-484fa4ca7528","2fc19111-1b5f-4952-bffd-c474317f56a2","e3c7311c-81be-488a-848f-2de9cbffe9cc"};

        for(int j =0;j<ids.length;j++){
            String id = ids[j];
            String url = "https://jpapi.hz.taeapp.com/v1/orgs/0/knowledges/" + id + "/catalog";
            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("token", "AAAAAFyRQlNZgpqdS4nkkf1I-IM2c80_XumN3Kusbu3GOYn2o2NrzJH-QbM0mn-SUxteQ2ch_n1CEMzyG5QLobaLpnA0Oj0Yz2A1ehH3R-BiSIDrUKnJAOY-oQ-fjHnh8IiqJHH14wGyliWKSn7RKaEnvIM");
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
            Thread.sleep(500);
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


    @Test
    public void testGet() throws Exception {

        for(int i=1019;i<5000;i++){
            try{
                String result = doGet("https://www.okfree.men/file-"+ i +".html",null);
                int begin = result.indexOf("soundFile: \"");
                int end = result.indexOf("\",titles");
                String durl = result.substring(begin+12,end);

                begin = result.indexOf("titles: \"");
                end = result.indexOf("\"});");
                String name = result.substring(begin+9,end);
                System.out.print(i+"      ");
                if(durl.startsWith("http")){
                    doDownload(durl,"D:\\download\\" + name,1,i);
                }
                else{
                    System.out.println(i+"出现异常");
                }
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
                System.out.println(i+"出现异常异常异常");
            }

        }

    }

    @Test
    public void testDownload() throws Exception {
//        doDownload("https://www.okfree.men/filestores/2019/06/14//7e8c076dadc9353fd7015cf2789e23df.mp3","D:\\download\\1.mp3");
    }

    public String  doGet(String url,String token)throws Exception{
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
    public String getReturn(HttpURLConnection connection) throws IOException {
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

    public static void main(String[] args) {

        DownloadThread thread1 = new DownloadThread(1,3244,3500);
        DownloadThread thread2 = new DownloadThread(2,3928,4300);
        DownloadThread thread3 = new DownloadThread(3,4301,4600);
        thread1.start();
        thread2.start();
        thread3.start();
//        thread4.start();
//        thread5.start();

    }

    public void  doDownload(String url,String filePath,int id,int i)throws Exception{

        Stopwatch started = Stopwatch.createStarted();


        URL serverUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //必须设置false，否则会自动redirect到重定向后的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();
        InputStream is = conn.getInputStream();
        File file = new File(filePath);
        FileOutputStream os = new FileOutputStream(file);
        BufferedOutputStream fos = new BufferedOutputStream(os);

        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = is.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }

        started.stop();
        long time = started.elapsed(TimeUnit.SECONDS);
        long kb = file.length()/1024;
        System.out.println("线程"+id+" , 第"+i+"  条，"+filePath+",耗时:"+time+" s,大小:"+kb/1024+" M，速度: "+ kb/time +" kb/s");


        fos.close();
        is.close();
    }



}

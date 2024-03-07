package com.navinfo.util;

/**
 * @author: ChenJie
 * @date 2019/6/27
 */
public class DownloadThread extends Thread {

    private int begin;
    private int end;
    private int id;



    public DownloadThread(int id ,int begin,int end){
        this.begin = begin;
        this.end = end;
        this.id = id;
    }

    @Override
    public void run(){
        HttpUtil httpUtil = new HttpUtil();
        for(int i=begin;i<=end;i++){
            try{
                String result = httpUtil.doGet("https://www.okfree.men/file-"+ i +".html",null);
                int begin = result.indexOf("soundFile: \"");
                int end = result.indexOf("\",titles");
                String durl = result.substring(begin+12,end);

                begin = result.indexOf("titles: \"");
                end = result.indexOf("\"});");
                String name = result.substring(begin+9,end);
                if(durl.startsWith("http")){
                    httpUtil.doDownload(durl,"D:\\download\\" + name,id,i);
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
}

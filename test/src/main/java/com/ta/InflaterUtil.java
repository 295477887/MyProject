package com.ta;

import com.navinfo.util.ArraysUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @Description: byte[],压缩,解压缩工具类
 * @Author: sunlin
 * @Date: 2019-03-01
 */
@Component
public class InflaterUtil {

    private static Logger logger = LoggerFactory.getLogger(InflaterUtil.class);

    private static int byteLength = 2048;

    /**
     * @Description: 压缩文件
     */
    public static byte[] compress(byte[] bytes){

        // Compress the bytes
        byte[] output = new byte[1024];
        Deflater deflater = new Deflater();
        deflater.setInput(bytes);
        deflater.finish();
        int compressedDataLength = deflater.deflate(output);
        deflater.end();
        System.out.println("Compressed Message length : " + compressedDataLength);
        return output;

    }

    /**
     * @Description: 解压缩文件
     */
    public static byte[] decompress(byte[] bytes, int length, boolean isRepeat){

        Inflater inflater = new Inflater();
        inflater.setInput(bytes, 0, length);// 解压缩byte,可以设置偏移量
        byte[] result = new byte[isRepeat?byteLength * 2:byteLength];
        try {
            int resultLength = inflater.inflate(result);
            if(inflater.finished() && resultLength > 0){
                return ArraysUtils.subarrays(result,0,resultLength);
            }
            InflaterUtil.decompress(bytes,length,true);
        } catch (DataFormatException e) {
            logger.error("解压缩文件出错,{}",e);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println((64 >> 6) & 3);
    }
}

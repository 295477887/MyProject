package com.navinfo.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: ChenJie
 * @date 2019/4/11
 */
@Component
public class VideoProperty {
    /**
     *
     * */
    @Value("${video.to.url.prefix}")
    private String toUrlPrefix;

    @Value("${video.width}")
    private int width;

    @Value("${video.height}")
    private int height;

    @Value("${video.audioChannels}")
    private int audioChannels;

    @Value("${video.interleaved:true}")
    private boolean interleaved;

    @Value("${video.videoCodec:28}")
    private int videoCodec;

    @Value("${video.format}")
    private String format="flv";

    @Value("${video.frameRate}")
    private int frameRate;

    @Value("${video.pixelFormat}")
    private int pixelFormat;

    public String getToUrlPrefix() {
        return toUrlPrefix;
    }

    public void setToUrlPrefix(String toUrlPrefix) {
        this.toUrlPrefix = toUrlPrefix;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAudioChannels() {
        return audioChannels;
    }

    public void setAudioChannels(int audioChannels) {
        this.audioChannels = audioChannels;
    }

    public boolean isInterleaved() {
        return interleaved;
    }

    public void setInterleaved(boolean interleaved) {
        this.interleaved = interleaved;
    }

    public int getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(int videoCodec) {
        this.videoCodec = videoCodec;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public int getPixelFormat() {
        return pixelFormat;
    }

    public void setPixelFormat(int pixelFormat) {
        this.pixelFormat = pixelFormat;
    }
}

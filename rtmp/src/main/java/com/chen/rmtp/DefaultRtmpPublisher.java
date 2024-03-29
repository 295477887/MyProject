package com.chen.rmtp;

import com.chen.rmtp.io.RtmpConnection;

/**
 * Srs implementation of an RTMP publisher
 *
 * @author francois, leoma, pedro
 */
public class DefaultRtmpPublisher implements RtmpPublisher {

  private RtmpConnection rtmpConnection;

  public DefaultRtmpPublisher() {
    rtmpConnection = new RtmpConnection();
  }

//  public DefaultRtmpPublisher() {
//
//  }

  @Override
  public boolean connect(String url) {
    return rtmpConnection.connect(url);
  }

  @Override
  public boolean publish(String publishType) {
    return rtmpConnection.publish(publishType);
  }

  @Override
  public void close() {
    rtmpConnection.close();
  }

  @Override
  public void publishVideoData(byte[] data, int size, int dts,int currentStreamId) {
    rtmpConnection.publishVideoData(data, size, dts, currentStreamId);
  }

  @Override
  public void publishAudioData(byte[] data, int size, int dts) {
    rtmpConnection.publishAudioData(data, size, dts);
  }

  @Override
  public void setVideoResolution(int width, int height) {
    rtmpConnection.setVideoResolution(width, height);
  }

  @Override
  public void setAuthorization(String user, String password) {
    rtmpConnection.setAuthorization(user, password);
  }
}

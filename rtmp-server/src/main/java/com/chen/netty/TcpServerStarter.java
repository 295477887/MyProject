package com.chen.netty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author: ChenJie
 * @date 2019/1/27
 */
@Component
public class TcpServerStarter {

    @Value("${netty.tcp.host}")
    private String tcpHost;

    @Value("${netty.tcp.port}")
    private int tcpPort;

    @Bean
    public TcpServer locationServer(TcpServerChannelInitializer channelInitializer) {
        TcpServer tcpServer = new TcpServer(tcpHost, tcpPort, channelInitializer);
        tcpServer.start();
        return tcpServer;
    }
}

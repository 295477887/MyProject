package com.chen.config;

import lombok.Data;

/**
 * @author: ChenJie
 * @date 2018/8/21
 */
@Data
public class MqttInboundProperties {
    private String url;
    private String username;
    private String password;
    private String clientId;
    private String topics;
}

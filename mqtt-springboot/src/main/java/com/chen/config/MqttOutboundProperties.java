package com.chen.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: ChenJie
 * @date 2018/8/21
 */
@Setter
@Getter
public class MqttOutboundProperties {
    private String urls;
    private String username;
    private String password;
    private String clientId;
    private String topic;
}

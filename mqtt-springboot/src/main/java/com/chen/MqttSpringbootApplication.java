package com.chen;

import com.chen.config.MqttProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @auth、
 * */
@SpringBootApplication
@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqttSpringbootApplication.class, args);
	}
}

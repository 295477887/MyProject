package com.chen.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author: ChenJie
 * @date 2018/11/9
 */
//@Component
public class KafkaProducer {
    @Autowired
    protected KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.producer.locationdata.topic}")
    private String topic;

//    @PostConstruct
    public void send(){
        for(int i=0;i<20;i++){
            kafkaTemplate.send(topic, String.valueOf(i),"hello "+i );
        }
    }
}

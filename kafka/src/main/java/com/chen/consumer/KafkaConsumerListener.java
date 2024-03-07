package com.chen.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author: ChenJie
 * @date 2017/10/28
 * 密集采集数据kafka消费类 dongfeng
 */
@Component
public class KafkaConsumerListener implements MessageListener<Object, Object> {
    private static Logger logger = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @Autowired
    protected KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public void onMessage(ConsumerRecord<Object, Object> consumerRecord) {
        try{
            String topic = consumerRecord.topic();
            String key =(String)consumerRecord.key();
            String value = (String)consumerRecord.value();
            logger.error("消费到数据topic:{},key:{},value:{}.",topic,key,value);

        }catch(Exception e){
            logger.error("Concentrated data consumed from kafka and save to redis error: ",e);
            e.printStackTrace();
        }
    }

}



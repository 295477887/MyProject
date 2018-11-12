package com.navinfo.aerozh.ni.gateway.hongyan.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenjc on 2017/07/05.
 */
@Configuration
@EnableKafka
public class KafkaProducerConfiguration {
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String producerServers;

    @Value("${spring.kafka.producer.key.serializer.class:kafka.serializer.StringEncoder}")
    private String producerKeySerializerClass;

    @Value("${spring.kafka.producer.value.serializer.class:kafka.serializer.StringEncoder}")
    private String producerValueSerializerClass;

    @Value("${spring.kafka.producer.batch.size:200}")
    private int batchSize;

    @Value("${spring.kafka.producer.buffer.memory:33554432}")
    private int bufferMemory;

    @Value("${spring.kafka.producer.request.timeout.ms:30000}")
    private int producerRequestTimeoutMs;

    @Value("${spring.kafka.producer.retries:0}")
    private int retries;

    @Value("${spring.kafka.producer.linger.ms:1}")
    private int lingerMs;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerServers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerKeySerializerClass);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerValueSerializerClass);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, producerRequestTimeoutMs);
        return props;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }

}
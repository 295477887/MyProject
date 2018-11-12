package com.chen.config;

import com.chen.consumer.KafkaConsumerListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ChenJie
 * @Description:
 * @Date 2017/10/9
 * @Modified by:
 */
@Component
public class KafkaConsumerConfiguration {

    @Value("${spring.kafka.bootstrap.servers}")
    protected String bootstrapServers;

    @Value("${spring.kafka.request.timeout.ms:30000}")
    private int requestTimeoutMs;

    @Value("${spring.kafka.session.timeout.ms:15000}")
    protected String sessionTimeoutMs;

    @Value("${spring.kafka.consumer.auto.commit.interval.ms:500}")
    protected String autoCommitIntervalMs;

    @Value("${spring.kafka.consumer.key.serializer.class:kafka.serializer.StringEncoder}")
    protected String consumerKeySerializerClass;

    @Value("${spring.kafka.consumer.value.serializer.class:kafka.serializer.StringEncoder}")
    protected String consumerValueSerializerClass;

    @Value("${spring.kafka.consumer.concurrency.size:1}")
    protected int concurrencySize;

    @Value("${spring.kafka.consumer.isAutoCommit:false}")
    protected boolean isAutoCommit;

    @Value("${spring.kafka.consumer.topic}")
    protected String topic;

    @Value("${spring.kafka.consumer.groupId}")
    protected String groupId;

    @Value("${spring.kafka.consumber.fetch.min.bytes:102400}")
    protected int maxFetchSize;

    @Value("${spring.kafka.consumer.auto.offset.reset}")
    protected String autoReset;



    @Autowired
    private KafkaConsumerListener kafkaConsumerListener;


    private ConsumerFactory<String, Object> consumerFactory(String groupId) {
        return new DefaultKafkaConsumerFactory(consumerConfigs(groupId));
    }

    private Map<String, Object> consumerConfigs(String groupId) {
        Map<String, Object> props = new HashMap<>(10);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, isAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMs);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerKeySerializerClass);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerValueSerializerClass);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, maxFetchSize);
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,autoReset);
        return props;
    }

    private ContainerProperties containerProperties(String topic,MessageListener messageListener) {
        ContainerProperties containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener(messageListener);
        return containerProperties;
    }


    public void createContainer(String groupId, String topic, MessageListener messageListener, int concurrencySize){
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer(consumerFactory(groupId), containerProperties(topic,messageListener));
        container.setConcurrency(concurrencySize);
        container.getContainerProperties().setPollTimeout(requestTimeoutMs);
        container.start();
    }

    /**
     * 由于kafka消费者消费实例启动和springboot的启动不再同一个线程里启动，所以等springboot容器启动完成后，在启动kafka，以避免
     * 在kafka线程先启动消费的时候调用spring容器还没有加载完的类实例,所以此处的启动挪到Application启动类的run方法里调用
     */
    @PostConstruct
    public void start(){

        createContainer(groupId,topic, kafkaConsumerListener,concurrencySize);

    }

}

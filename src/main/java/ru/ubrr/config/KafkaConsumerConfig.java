package ru.ubrr.config;

import ru.ubrr.dto.adapter.consumer.AdvanceLimitCalculationConsumerDTO;
import ru.ubrr.dto.adapter.consumer.CheckLimitStatusConsumerDTO;
import ru.ubrr.dto.adapter.consumer.ClientAssessmentConsumerDTO;
import ru.ubrr.dto.adapter.consumer.FinalLimitCalculationConsumerDTO;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

  @Autowired
  private KafkaCommonConfig commonConfig;



    @Bean
    public Map<String, Object> consumerManualCommitCommonGroupConfigs() {
        final Map<String, Object> config = new HashMap<>(commonConfig.consumerConfig());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, commonConfig.getGroupId());
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return config;
    }

    @Bean
    public Map<String, Object> consumerUniqueGroupEarliestConfigs() {
        final Map<String, Object> config = new HashMap<>(commonConfig.consumerConfig());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, commonConfig.getUniqueGroupPrefix());
        //+ UUID.randomUUID().toString());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);

        return config;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(commonConfig.consumerConfig(), new StringDeserializer(), new StringDeserializer());
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        return concurrentBatchContainerFactory(consumerFactory());
    }

    private <T> ConsumerFactory<String, T> genericConsumerFactory(Class<T> tClass, Map<String, Object> props) {
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(tClass, commonConfig.getMapper(), false));
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> commonManualContainerFactory(ConsumerFactory<String, T> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setPollTimeout(2000);
        return factory;
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> concurrentManualCommitContainerFactory(ConsumerFactory<String, T> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = commonManualContainerFactory(consumerFactory);
        factory.setConcurrency(4);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setSyncCommits(true);
        return factory;
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> concurrentBatchContainerFactory(ConsumerFactory<String, T> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = commonManualContainerFactory(consumerFactory);
        factory.setConcurrency(1);
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClientAssessmentConsumerDTO> callZeroOneListenerContainerFactory() {
        return concurrentManualCommitContainerFactory(genericConsumerFactory(ClientAssessmentConsumerDTO.class, consumerManualCommitCommonGroupConfigs()));
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AdvanceLimitCalculationConsumerDTO> advanceLimitCalculationConsumerDTOContainerFactory() {
        return concurrentManualCommitContainerFactory(genericConsumerFactory(AdvanceLimitCalculationConsumerDTO.class, consumerManualCommitCommonGroupConfigs()));
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CheckLimitStatusConsumerDTO> checkLimitStatusConsumerDTOContainerFactory() {
        return concurrentManualCommitContainerFactory(genericConsumerFactory(CheckLimitStatusConsumerDTO.class, consumerManualCommitCommonGroupConfigs()));
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FinalLimitCalculationConsumerDTO> finalLimitCalculationConsumerDTOContainerFactory() {
        return concurrentManualCommitContainerFactory(genericConsumerFactory(FinalLimitCalculationConsumerDTO.class, consumerManualCommitCommonGroupConfigs()));
    }

}

package ru.ubrr.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Configuration
@EnableKafka
@Data
public class KafkaCommonConfig {
	  @Value("${spring.kafka.bootstrap.servers}")
	    private String servers;

	    @Value("${spring.kafka.group.id}")
	    private String groupId;

	    @Value("${spring.kafka.group.unique.prefix}")
	    private String uniqueGroupPrefix;
	    
	    @Autowired
	    private ObjectMapper mapper;
	    
	    @Bean
	    public Map<String, Object> consumerConfig() {
	        final Map<String, Object> config = new HashMap<>();
	        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
	        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
	        return config;
	    }
	    
	    @Bean
	    public Map<String, Object> producerConfig() {
	        final Map<String, Object> config = new HashMap<>();
	        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
	        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	        return config;
	    }
}

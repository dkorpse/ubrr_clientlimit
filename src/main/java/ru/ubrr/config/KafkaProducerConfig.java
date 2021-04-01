package ru.ubrr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import ru.ubrr.dto.adapter.producer.AdvanceLimitCalculationProducerDTO;
import ru.ubrr.dto.adapter.producer.CheckLimitStatusProducerDTO;
import ru.ubrr.dto.adapter.producer.ClientAssessmentProducerDTO;
import ru.ubrr.dto.adapter.producer.FinalLimitCalculationProducerDTO;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

	@Autowired
	private KafkaCommonConfig commonConfig;

	@Bean
	public ProducerFactory<String, ClientAssessmentProducerDTO> clientAssessmentProducerDTOProducerFactory() {
		return new DefaultKafkaProducerFactory<>(commonConfig.producerConfig());
	}

	@Bean
	public KafkaTemplate<String, ClientAssessmentProducerDTO> clientAssessmentProducerDTOProducerTemplate() {
		return new KafkaTemplate<>(clientAssessmentProducerDTOProducerFactory());
	}
	
	@Bean
	public ProducerFactory<String, AdvanceLimitCalculationProducerDTO> advanceLimitCalculationProducerDTOProducerFactory() {
		return new DefaultKafkaProducerFactory<>(commonConfig.producerConfig());
	}

	@Bean
	public KafkaTemplate<String, AdvanceLimitCalculationProducerDTO> advanceLimitCalculationProducerDTOProducerTemplate() {
		return new KafkaTemplate<>(advanceLimitCalculationProducerDTOProducerFactory());
	}
	
	@Bean
	public ProducerFactory<String, CheckLimitStatusProducerDTO> checkLimitStatusProducerDTOProducerFactory() {
		return new DefaultKafkaProducerFactory<>(commonConfig.producerConfig());
	}

	@Bean
	public KafkaTemplate<String, CheckLimitStatusProducerDTO> checkLimitStatusProducerDTOProducerTemplate() {
		return new KafkaTemplate<>(checkLimitStatusProducerDTOProducerFactory());
	}
	
	@Bean
	public ProducerFactory<String, FinalLimitCalculationProducerDTO> finalLimitCalculationProducerDTOProducerFactory() {
		return new DefaultKafkaProducerFactory<>(commonConfig.producerConfig());
	}

	@Bean
	public KafkaTemplate<String, FinalLimitCalculationProducerDTO> finalLimitCalculationProducerDTOProducerTemplate() {
		return new KafkaTemplate<>(finalLimitCalculationProducerDTOProducerFactory());
	}
	
	@Bean
	public ProducerFactory<String, String> stringProducerFactory() {
		return new DefaultKafkaProducerFactory<>(commonConfig.producerConfig());
	}

	@Bean
	public KafkaTemplate<String, String> stringProducerTemplate() {
		return new KafkaTemplate<>(stringProducerFactory());
	}

}

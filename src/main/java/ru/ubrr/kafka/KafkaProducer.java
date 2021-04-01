package ru.ubrr.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.ubrr.config.KafkaProducerConfig;
import ru.ubrr.dto.adapter.producer.AdvanceLimitCalculationProducerDTO;
import ru.ubrr.dto.adapter.producer.CheckLimitStatusProducerDTO;
import ru.ubrr.dto.adapter.producer.ClientAssessmentProducerDTO;
import ru.ubrr.dto.adapter.producer.FinalLimitCalculationProducerDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

	@Value("${spring.kafka.producer.topic.client.assesment}")
    private String topicClienAssessment;
	
	@Value("${spring.kafka.producer.topic.advance.limit.calculation}")
    private String topicAdvanceLimitCalculation;
	
	@Value("${spring.kafka.producer.topic.final.limit.calculation}")
    private String topicFinalLimitCalculation;
	
	@Value("${spring.kafka.producer.topic.check.limit.status}")
    private String topicCheckLimitStatus;
	
	@Value("${spring.kafka.producer.error.topic}")
    private String errorTopic;
    
    private final KafkaProducerConfig producerConfig;

    public void sendClientAssessmentMessage(ClientAssessmentProducerDTO message) {
        log.info("Trying to send {} to kafka", message);
        producerConfig.clientAssessmentProducerDTOProducerTemplate().send(topicClienAssessment, message);
        log.info("Message {} sent to kafka", message);
    }
    
    public void sendAdvanceLimitCalculationMessage(AdvanceLimitCalculationProducerDTO message) {
        log.info("Trying to send {} to kafka", message);
        producerConfig.advanceLimitCalculationProducerDTOProducerTemplate().send(topicAdvanceLimitCalculation, message);
        log.info("Message {} sent to kafka", message);
    }
    
    public void sendCheckLimitStatusProducerMessage(CheckLimitStatusProducerDTO message) {
        log.info("Trying to send {} to kafka", message);
        producerConfig.checkLimitStatusProducerDTOProducerTemplate().send(topicCheckLimitStatus, message);
        log.info("Message {} sent to kafka", message);
    }
    
    public void sendFinalLimitCalculationProducerMessage(FinalLimitCalculationProducerDTO message) {
        log.info("Trying to send {} to kafka", message);
        producerConfig.finalLimitCalculationProducerDTOProducerTemplate().send(topicFinalLimitCalculation, message);
        log.info("Message {} sent to kafka", message);
    }

    public void sendErrorMessage(String message) {
        log.info("Trying to send error {} to kafka", message);
        this.producerConfig.stringProducerTemplate().send(errorTopic, message);
        log.info("Error message {} sent to kafka", message);
    }

}

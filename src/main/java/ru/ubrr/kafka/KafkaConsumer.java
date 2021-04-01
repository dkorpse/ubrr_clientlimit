package ru.ubrr.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.ubrr.dto.adapter.consumer.AdvanceLimitCalculationConsumerDTO;
import ru.ubrr.dto.adapter.consumer.CheckLimitStatusConsumerDTO;
import ru.ubrr.dto.adapter.consumer.ClientAssessmentConsumerDTO;
import ru.ubrr.dto.adapter.consumer.FinalLimitCalculationConsumerDTO;
import ru.ubrr.dto.adapter.producer.AdvanceLimitCalculationProducerDTO;
import ru.ubrr.dto.adapter.producer.CheckLimitStatusProducerDTO;
import ru.ubrr.dto.adapter.producer.ClientAssessmentProducerDTO;
import ru.ubrr.dto.adapter.producer.FinalLimitCalculationProducerDTO;
import ru.ubrr.service.adapter.AdvanceLimitCalculationAdapterService;
import ru.ubrr.service.adapter.CheckLimitStatusAdapterService;
import ru.ubrr.service.adapter.ClientAssessmentAdapterService;
import ru.ubrr.service.adapter.FinalLimitCalculationAdapterService;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
	
	Logger logger = LogManager.getLogger(KafkaConsumer.class);

	@Autowired
	private ClientAssessmentAdapterService callZeroOneAdapterService;
	
	@Autowired
	private AdvanceLimitCalculationAdapterService advanceLimitCalculationAdapterService;
	
	@Autowired
	private CheckLimitStatusAdapterService checkLimitStatusAdapterService;
	
	@Autowired
	private FinalLimitCalculationAdapterService finalLimitCalculationAdapterService;
	
	@Autowired
	private KafkaProducer producer;
	
    @KafkaListener(topics = {"${spring.kafka.consumer.topic.client.assesment}"}, containerFactory = "callZeroOneListenerContainerFactory")
    public void consumeClientAssessment(final ConsumerRecord<String, ClientAssessmentConsumerDTO> message) {
    	
        logger.info("Consumed message from kafka: {}", message.value());
        final ClientAssessmentConsumerDTO dto=message.value();
        ClientAssessmentProducerDTO result = null;
        try {
        result = callZeroOneAdapterService.call(dto);
        result.setId(dto.getId());
        producer.sendClientAssessmentMessage(result);
        }catch(Exception e) {
        	logger.info("Error {}", e.getMessage());
        	producer.sendErrorMessage(e.getMessage());
        }      
    }
    
    @KafkaListener(topics = {"${spring.kafka.consumer.topic.advance.limit.calculation}"}, containerFactory = "advanceLimitCalculationConsumerDTOContainerFactory")
    public void consumeAdvanceLimitCalculation(final ConsumerRecord<String, AdvanceLimitCalculationConsumerDTO> message) {
 
        log.info("Consumed message from kafka: {}", message.value());
        final AdvanceLimitCalculationConsumerDTO dto=message.value();
        AdvanceLimitCalculationProducerDTO result = null;
        try {
        result = advanceLimitCalculationAdapterService.call(dto);
        result.setId(dto.getId());
        producer.sendAdvanceLimitCalculationMessage(result);
        }catch(Exception e) {
        	log.info("Error {}", e.getMessage());
        	producer.sendErrorMessage(e.getMessage());
        }      
    }
    
    @KafkaListener(topics = {"${spring.kafka.consumer.topic.final.limit.calculation}"}, containerFactory = "finalLimitCalculationConsumerDTOContainerFactory")
    public void consumeFinalLimitCalculation(final ConsumerRecord<String, FinalLimitCalculationConsumerDTO> message) {
 
        log.info("Consumed message from kafka: {}", message.value());
        final FinalLimitCalculationConsumerDTO dto=message.value();
        FinalLimitCalculationProducerDTO result = null;
        try {
        result = finalLimitCalculationAdapterService.call(dto);
        result.setId(dto.getId());
        producer.sendFinalLimitCalculationProducerMessage(result);
        }catch(Exception e) {
        	log.info("Error {}", e.getMessage());
        	producer.sendErrorMessage(e.getMessage());
        }      
    }
    
    @KafkaListener(topics = {"${spring.kafka.consumer.topic.check.limit.status}"}, containerFactory = "checkLimitStatusConsumerDTOContainerFactory")
    public void consumeCheckLimit(final ConsumerRecord<String, CheckLimitStatusConsumerDTO> message) {
 
        log.info("Consumed message from kafka: {}", message.value());
        final CheckLimitStatusConsumerDTO dto=message.value();
        CheckLimitStatusProducerDTO result = null;
        try {
        result = checkLimitStatusAdapterService.call(dto);
        result.setId(dto.getId());
        producer.sendCheckLimitStatusProducerMessage(result);
        }catch(Exception e) {
        	log.info("Error {}", e.getMessage());
        	producer.sendErrorMessage(e.getMessage());
        }      
    }
}

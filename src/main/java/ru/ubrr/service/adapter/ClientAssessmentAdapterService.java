package ru.ubrr.service.adapter;

import ru.ubrr.dto.adapter.consumer.ClientAssessmentConsumerDTO;
import ru.ubrr.dto.adapter.producer.ClientAssessmentProducerDTO;

public interface ClientAssessmentAdapterService {
	
	ClientAssessmentProducerDTO call(ClientAssessmentConsumerDTO dto);
}

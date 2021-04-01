package ru.ubrr.service.vkl;

import ru.ubrr.dto.adapter.producer.ClientAssessmentProducerDTO;

public interface ClientAssessmentVKLService {
	
	ClientAssessmentProducerDTO call(String partner, String product, String call, Integer inet);
}

package ru.ubrr.service.adapter;

import ru.ubrr.dto.adapter.consumer.AdvanceLimitCalculationConsumerDTO;
import ru.ubrr.dto.adapter.producer.AdvanceLimitCalculationProducerDTO;

public interface AdvanceLimitCalculationAdapterService {
	AdvanceLimitCalculationProducerDTO call(AdvanceLimitCalculationConsumerDTO dto);
}

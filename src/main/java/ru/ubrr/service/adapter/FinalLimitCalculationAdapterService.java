package ru.ubrr.service.adapter;

import ru.ubrr.dto.adapter.consumer.FinalLimitCalculationConsumerDTO;
import ru.ubrr.dto.adapter.producer.FinalLimitCalculationProducerDTO;

public interface FinalLimitCalculationAdapterService {
	FinalLimitCalculationProducerDTO call(FinalLimitCalculationConsumerDTO dto);
}

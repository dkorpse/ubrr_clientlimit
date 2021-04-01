package ru.ubrr.service.adapter;

import ru.ubrr.dto.adapter.consumer.CheckLimitStatusConsumerDTO;
import ru.ubrr.dto.adapter.producer.CheckLimitStatusProducerDTO;

public interface CheckLimitStatusAdapterService {
	CheckLimitStatusProducerDTO call(CheckLimitStatusConsumerDTO dto);
}

package ru.ubrr.service.vkl;

import ru.ubrr.dto.adapter.producer.FinalLimitCalculationProducerDTO;

public interface FinalLimitCalculationVKLService {
	FinalLimitCalculationProducerDTO call(String partner, String anketaId, String product, String call, Integer inet);

}

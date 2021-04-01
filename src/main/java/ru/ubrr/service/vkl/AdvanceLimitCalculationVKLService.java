package ru.ubrr.service.vkl;

import ru.ubrr.dto.adapter.producer.AdvanceLimitCalculationProducerDTO;

public interface AdvanceLimitCalculationVKLService {
	AdvanceLimitCalculationProducerDTO call(String partner, String anketaId, String product, String call, Integer inet);
}

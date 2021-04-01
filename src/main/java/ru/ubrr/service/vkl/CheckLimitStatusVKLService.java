package ru.ubrr.service.vkl;

import ru.ubrr.dto.adapter.producer.CheckLimitStatusProducerDTO;

public interface CheckLimitStatusVKLService {
	CheckLimitStatusProducerDTO call(String anketaId, String date);
}

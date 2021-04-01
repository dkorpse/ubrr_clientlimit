package ru.ubrr.service.adapter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.ubrr.component.AliasConstants;
import ru.ubrr.dto.adapter.consumer.CheckLimitStatusConsumerDTO;
import ru.ubrr.dto.adapter.producer.CheckLimitStatusProducerDTO;
import ru.ubrr.exceptions.AdapterException;
import ru.ubrr.service.adapter.CheckLimitStatusAdapterService;
import ru.ubrr.service.vkl.CheckLimitStatusVKLService;

@Slf4j
@Service
public class CheckLimitStatusAdapterServiceImpl implements CheckLimitStatusAdapterService{

	@Autowired
	private CheckLimitStatusVKLService checkLimitStatusVKLService;
	
	@Override
	public CheckLimitStatusProducerDTO call(CheckLimitStatusConsumerDTO dto) {
		log.info(AliasConstants.ADAPTER_SERVICE_CALL_0_CALL_1_START_REQUEST_FOR_PARTNER, dto.getAnketaId());		
		CheckLimitStatusProducerDTO result = null;
		try {
			result = checkLimitStatusVKLService.call(dto.getAnketaId(), dto.getCreateDateTime());
			result.setId(dto.getId());
		} catch (Exception e) {
			throw new AdapterException(dto.getId(), e.getMessage());
		}
		log.info(AliasConstants.ADAPTER_SERVICE_CALL_0_CALL_1_END_REQUEST_FOR_PARTNER_WITH_RESULT, dto.getAnketaId(), result);   
		return result;
	}

}

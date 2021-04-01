package ru.ubrr.service.adapter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.ubrr.component.AliasConstants;
import ru.ubrr.dto.adapter.consumer.AdvanceLimitCalculationConsumerDTO;
import ru.ubrr.dto.adapter.producer.AdvanceLimitCalculationProducerDTO;
import ru.ubrr.exceptions.AdapterException;
import ru.ubrr.service.adapter.AdvanceLimitCalculationAdapterService;
import ru.ubrr.service.vkl.AdvanceLimitCalculationVKLService;

@Slf4j
@Service
public class AdvanceLimitCalculationAdapterServiceImpl implements AdvanceLimitCalculationAdapterService {
	
	@Autowired
	private AdvanceLimitCalculationVKLService advanceLimitCalculationVKLService;
	
	public AdvanceLimitCalculationProducerDTO call(final AdvanceLimitCalculationConsumerDTO dto) {
	log.info(AliasConstants.ADAPTER_SERVICE_ADVANCED_LIMIT_CALCULATION_START_REQUEST_FOR_PARTNER, dto.getDpCode());		
	AdvanceLimitCalculationProducerDTO result = null;
	try {
		result = advanceLimitCalculationVKLService.call(dto.getDpCode(), dto.getAnketaID(), dto.getProduct(), dto.getCall(), dto.getInet());
		result.setId(dto.getId());
	} catch (Exception e) {
		throw new AdapterException(dto.getDpCode(), e.getMessage());
	}
	log.info(AliasConstants.ADAPTER_SERVICE_ADVANCED_LIMIT_CALCULATION_END_REQUEST_FOR_PARTNER_WITH_RESULT, dto.getDpCode(), result);   
	return result;
	}

}

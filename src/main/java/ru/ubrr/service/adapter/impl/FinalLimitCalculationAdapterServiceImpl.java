package ru.ubrr.service.adapter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.ubrr.component.AliasConstants;
import ru.ubrr.dto.adapter.consumer.FinalLimitCalculationConsumerDTO;
import ru.ubrr.dto.adapter.producer.FinalLimitCalculationProducerDTO;
import ru.ubrr.exceptions.AdapterException;
import ru.ubrr.service.adapter.FinalLimitCalculationAdapterService;
import ru.ubrr.service.vkl.FinalLimitCalculationVKLService;


@Slf4j
@Service
public class FinalLimitCalculationAdapterServiceImpl implements FinalLimitCalculationAdapterService{

	@Autowired
	private FinalLimitCalculationVKLService finalLimitCalculationVKLService;
	
	@Override
	public FinalLimitCalculationProducerDTO call(final FinalLimitCalculationConsumerDTO dto) {
		log.info(AliasConstants.ADAPTER_SERVICE_FINAL_LIMIT_CALCULATION_START_REQUEST_FOR_PARTNER, dto.getDpCode());		
		FinalLimitCalculationProducerDTO result = null;
		try {
			result = finalLimitCalculationVKLService.call(dto.getDpCode(), dto.getAnketaID(), dto.getProduct(), dto.getCall(), dto.getInet());
			result.setId(dto.getId());
		} catch (Exception e) {
			throw new AdapterException(dto.getDpCode(), e.getMessage());
		}
		log.info(AliasConstants.ADAPTER_SERVICE_FINAL_LIMIT_CALCULATION_END_REQUEST_FOR_PARTNER_WITH_RESULT, dto.getDpCode(), result);   
		return result;
	}

}

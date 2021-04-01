package ru.ubrr.service.adapter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.ubrr.component.AliasConstants;
import ru.ubrr.dto.adapter.consumer.ClientAssessmentConsumerDTO;
import ru.ubrr.dto.adapter.producer.ClientAssessmentProducerDTO;
import ru.ubrr.exceptions.AdapterException;
import ru.ubrr.service.adapter.ClientAssessmentAdapterService;
import ru.ubrr.service.vkl.ClientAssessmentVKLService;

@Slf4j
@Service
public class ClientAssessmentAdapterServiceImpl implements ClientAssessmentAdapterService{

	@Autowired
	private ClientAssessmentVKLService clientAssessmentVKLService;
	
	@Override
	public ClientAssessmentProducerDTO call(final ClientAssessmentConsumerDTO dto) {
		log.info(AliasConstants.ADAPTER_SERVICE_CALL_0_CALL_1_START_REQUEST_FOR_PARTNER, dto.getDpCode());		
		ClientAssessmentProducerDTO result = null;
		try {
			result = clientAssessmentVKLService.call(dto.getDpCode(), dto.getProduct(), dto.getCall(), dto.getInet());
			result.setId(dto.getId());
		} catch (Exception e) {
			throw new AdapterException(dto.getDpCode(), e.getMessage());
		}
		log.info(AliasConstants.ADAPTER_SERVICE_CALL_0_CALL_1_END_REQUEST_FOR_PARTNER_WITH_RESULT, dto.getDpCode(), result);   
		return result;
	}

}

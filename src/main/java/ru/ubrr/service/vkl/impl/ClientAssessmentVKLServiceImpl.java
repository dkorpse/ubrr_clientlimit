package ru.ubrr.service.vkl.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.ubrr.component.AliasConstants;
import ru.ubrr.dto.adapter.producer.ClientAssessmentProducerDTO;
import ru.ubrr.exceptions.VKLCallException;
import ru.ubrr.service.vkl.ClientAssessmentVKLService;

@Slf4j
@Service
public class ClientAssessmentVKLServiceImpl implements ClientAssessmentVKLService {

	private final static String CALL_ZERO_ONE = "{call UBRR_VLV.ubrr_service_calc_limit.assessment_client(?,?,?,?,?)}";
	@Autowired
	private DataSource dataSource;

	@Override
	public ClientAssessmentProducerDTO call(final String partner, final String product, final String call,
			final Integer inet) {
		log.debug(AliasConstants.VKL_SERVICE_CALL_0_CALL_1_START_REQUEST_FOR_PARTNER, partner);

		final ClientAssessmentProducerDTO result = new ClientAssessmentProducerDTO();
		try (Connection con = dataSource.getConnection(); CallableStatement cstmt = con.prepareCall(CALL_ZERO_ONE);) {
			cstmt.setString(1, partner);
			cstmt.setString(2, product);
			cstmt.setString(3, call);
			cstmt.setInt(4, inet);
			cstmt.registerOutParameter(5, Types.INTEGER);
			cstmt.execute();
			final Integer res = cstmt.getInt(5);
			result.setResult(String.valueOf(res));
		} catch (Exception e) {
			throw new VKLCallException(partner, e.getMessage());
		}
		log.info(AliasConstants.VKL_SERVICE_CALL_0_CALL_1_END_REQUEST_FOR_PARTNER_WITH_RESULT, partner, result);
		return result;
	}

}

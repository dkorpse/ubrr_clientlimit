package ru.ubrr.service.vkl.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.ubrr.component.AliasConstants;
import ru.ubrr.dto.adapter.producer.FinalLimitCalculationProducerDTO;
import ru.ubrr.exceptions.VKLCallException;
import ru.ubrr.service.vkl.FinalLimitCalculationVKLService;

@Slf4j
@Service
public class FinalLimitCalculationVKLServiceImpl implements FinalLimitCalculationVKLService {

	private final static String CALL = "{call UBRR_VLV.ubrr_service_calc_limit.finish_calc_limit(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	@Autowired
	private DataSource dataSource;

	public FinalLimitCalculationProducerDTO call(final String partner, final String anketaId,final String product, final String call,
			final Integer inet) {
		log.info(AliasConstants.VKL_SERVICE_FINAL_LIMIT_CALCULATION_START_REQUEST_FOR_PARTNER, partner);
		final FinalLimitCalculationProducerDTO result = new FinalLimitCalculationProducerDTO();
	
		try (Connection con = dataSource.getConnection();  CallableStatement cstmt = con.prepareCall(CALL);){
            cstmt.setString(1, partner);
            cstmt.setString(2, anketaId);
            cstmt.setString(3, product);
            cstmt.setString(4, call);
            cstmt.setInt(5, inet);
            
            cstmt.registerOutParameter(6, Types.INTEGER);
            cstmt.registerOutParameter(7, Types.INTEGER);
            cstmt.registerOutParameter(8, Types.DOUBLE);
            cstmt.registerOutParameter(9, Types.DOUBLE);
            cstmt.registerOutParameter(10, Types.DOUBLE);
            cstmt.registerOutParameter(11, Types.DOUBLE);
            cstmt.registerOutParameter(12, Types.VARCHAR);
            cstmt.registerOutParameter(13, Types.VARCHAR);
            cstmt.execute();
         
            final Integer res = cstmt.getInt(6);
            final Integer rtdmId = cstmt.getInt(7);
            final Double limit = cstmt.getDouble(8);
            final Double term=cstmt.getDouble(9);
            final String textClient = cstmt.getString(10);
            final String textMiddle = cstmt.getString(11);
            final Double prc = cstmt.getDouble(12);

            cstmt.execute();
            
            result.setResult(res.toString());
            result.setRtdmId(rtdmId);
            result.setLimit(limit);
            result.setTerm(term);
            result.setTextClient(textClient);
            result.setPrc(prc);
            result.setTextMidl(textMiddle);
            
        } catch (Exception e) {
        	throw new VKLCallException(partner, e.getMessage());
        }		
		log.info(AliasConstants.VKL_SERVICE_FINAL_LIMIT_CALCULATION_END_REQUEST_FOR_PARTNER_WITH_RESULT, partner, result);
		return result;
	}

}

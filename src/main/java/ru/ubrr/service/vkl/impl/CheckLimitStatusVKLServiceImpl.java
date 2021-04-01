package ru.ubrr.service.vkl.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.ubrr.component.AliasConstants;
import ru.ubrr.dto.adapter.producer.CheckLimitStatusProducerDTO;
import ru.ubrr.exceptions.VKLCallException;
import ru.ubrr.service.vkl.CheckLimitStatusVKLService;
import ru.ubrr.util.Utils;

@Slf4j
@Service
public class CheckLimitStatusVKLServiceImpl implements CheckLimitStatusVKLService {

	private final static String CALL = "{call UBRR_VLV.ubrr_service_calc_limit.get_status_calc_limit(?,?,?,?,?,?)}";
	@Autowired
    private  DataSource dataSource; 
	
	@Override
	public CheckLimitStatusProducerDTO call(final String anketaId, final String date) {
		log.info(AliasConstants.VKL_SERVICE_CHECK_LIMIT_STATUS_START_REQUEST_FOR_PARTNER, anketaId);
		final CheckLimitStatusProducerDTO result = new CheckLimitStatusProducerDTO();
		
		try (Connection con = dataSource.getConnection();  CallableStatement cstmt = con.prepareCall(CALL);){
			 cstmt.setString(1, anketaId);
			 
	          cstmt.setDate(2, Utils.convertDate(date, null));
	           
	           
	            cstmt.registerOutParameter(3, Types.INTEGER);
	            cstmt.registerOutParameter(4, Types.DOUBLE);
	            cstmt.registerOutParameter(5, Types.DOUBLE);
	            cstmt.registerOutParameter(6, Types.DOUBLE);
	            cstmt.execute();
	         
	            final Integer res = cstmt.getInt(6);
	            final Integer rtdmId = cstmt.getInt(7);
	            final Double limit = cstmt.getDouble(8);
	            final Double prc = cstmt.getDouble(9);
	            final Double term=cstmt.getDouble(10);
	           
	            result.setResult(String.valueOf(res));
	            result.setLimit(limit);
	            result.setPrc(prc);
	            result.setRtdmId(rtdmId);
	            result.setTerm(term);
        } catch (Exception e) {
        	throw new VKLCallException(anketaId, e.getMessage());
        }		
		log.info(AliasConstants.VKL_SERVICE_CHECK_LIMIT_STATUS_END_REQUEST_FOR_PARTNER_WITH_RESULT, anketaId, result);
		return result;
	}

}

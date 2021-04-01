package ru.ubrr.service.vkl.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.ubrr.component.AliasConstants;
import ru.ubrr.dto.adapter.producer.AdvanceLimitCalculationProducerDTO;
import ru.ubrr.exceptions.VKLCallException;
import ru.ubrr.service.vkl.AdvanceLimitCalculationVKLService;

@Slf4j
@Service
public class AdvanceLimitCalculationVKLServiceImpl implements AdvanceLimitCalculationVKLService{
	
	private final static String CALL = "{call UBRR_VLV.ubrr_service_calc_limit.pre_calc_limit(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	@Autowired
    private  DataSource dataSource; 
	
	@Override
	public AdvanceLimitCalculationProducerDTO call(final String partner,final String anketaId, final String product, final String call,
			final Integer inet) {
		log.info(AliasConstants.VKL_SERVICE_ADVANCED_LIMIT_CALCULATION_START_REQUEST_FOR_PARTNER, partner);
		final AdvanceLimitCalculationProducerDTO result = new AdvanceLimitCalculationProducerDTO();
	
		try (Connection con = dataSource.getConnection();  CallableStatement cstmt = con.prepareCall(CALL);){
            cstmt.setString(1, partner);
            cstmt.setString(2, anketaId);
            cstmt.setString(3, product);
            cstmt.setString(4, call);
            cstmt.setInt(5, inet);
            
            cstmt.registerOutParameter(6, Types.INTEGER);
            cstmt.registerOutParameter(7, Types.INTEGER);
            cstmt.registerOutParameter(8, Types.VARCHAR);
            cstmt.registerOutParameter(9, Types.VARCHAR);
            cstmt.registerOutParameter(10, Types.INTEGER);
            cstmt.registerOutParameter(11, Types.INTEGER);
            cstmt.registerOutParameter(12, Types.INTEGER);
            cstmt.registerOutParameter(13, Types.INTEGER);
            cstmt.execute();
         
            final Integer res = cstmt.getInt(6);
            final Integer rtdmId = cstmt.getInt(7);
            final String dLimitStart = cstmt.getString(8);
            final String sLimitEnd=cstmt.getString(9);
            final Integer pd = cstmt.getInt(10);
            final Integer npd = cstmt.getInt(11);
            final Integer prc = cstmt.getInt(12);
            final Integer zp = cstmt.getInt(13);

            cstmt.execute();
            
            result.setResult(res.toString());
            result.setRtdmId(rtdmId);
            result.setDLimitStart(dLimitStart);
            result.setDLimitEnd(sLimitEnd);
            result.setLimitNPD(String.valueOf(npd));
            result.setLimitPD(String.valueOf(pd));
            result.setPrc(String.valueOf(prc));
            result.setZp(String.valueOf(zp));
            
        } catch (Exception e) {
        	throw new VKLCallException(partner, e.getMessage());
        }		
		log.info(AliasConstants.VKL_SERVICE_ADVANCED_LIMIT_CALCULATION_END_REQUEST_FOR_PARTNER_WITH_RESULT, partner, result);
		return result;
	}

}

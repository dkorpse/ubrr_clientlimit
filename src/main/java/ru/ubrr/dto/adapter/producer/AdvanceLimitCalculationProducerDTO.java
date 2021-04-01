package ru.ubrr.dto.adapter.producer;

import lombok.Data;

@Data
public class AdvanceLimitCalculationProducerDTO {
	private String id;
	private String result;
	private Integer rtdmId;
	private String dLimitStart;
	private String dLimitEnd;
	private String limitPD;
	private String limitNPD;
	private String prc;
	private String zp;
}

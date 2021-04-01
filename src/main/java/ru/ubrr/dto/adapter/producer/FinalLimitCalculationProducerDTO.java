package ru.ubrr.dto.adapter.producer;

import lombok.Data;

@Data
public class FinalLimitCalculationProducerDTO {
	private String id;
	private String result;
	private Integer rtdmId;
	private Double limit;
	private Double prc;
	private Double term;
	private String textClient;
	private String textMidl;
}

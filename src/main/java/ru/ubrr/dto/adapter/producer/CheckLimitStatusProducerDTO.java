package ru.ubrr.dto.adapter.producer;

import lombok.Data;

@Data
public class CheckLimitStatusProducerDTO {
	private String id;
	private String result;
	private Integer rtdmId;
	private Double limit;
	private Double prc;
	private Double term;
}

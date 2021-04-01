package ru.ubrr.dto.adapter.consumer;

import lombok.Data;

@Data
public class FinalLimitCalculationConsumerDTO {
	private String id;
	private String dpCode ;
	private String anketaID ;
	private String product ;
	private String call ;
	private Integer inet;
}

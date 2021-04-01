package ru.ubrr.exceptions;

import org.springframework.util.StringUtils;

import ru.ubrr.component.AliasConstants;

public class AdapterException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public AdapterException(final String message, final String partner) {
		super(buildMessage(message, partner));
	}

	private static String buildMessage(final String message, final String partner) {
		final StringBuilder sb = new StringBuilder(AliasConstants.ADAPTER_SERVICE);
		sb.append(" ");
		sb.append(AliasConstants.CALL_0_CALL_1);
		sb.append(" call error for partner ");
		sb.append(partner);
		if(!StringUtils.hasText(message)) {
			sb.append(". Caused by ");
			sb.append(message);
		}
		return sb.toString();
	}

}

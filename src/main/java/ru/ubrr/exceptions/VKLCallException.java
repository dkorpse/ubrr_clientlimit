package ru.ubrr.exceptions;

import ru.ubrr.component.AliasConstants;

public class VKLCallException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public VKLCallException(final String partner, final String message) {
		super(buildMessage(partner, message));
	}

	private static String buildMessage(final String partner, final String message) {
		final StringBuilder sb = new StringBuilder(AliasConstants.VKL_SERVICE);
		sb.append(" ");
		sb.append(" call error for partner ");
		sb.append(partner);
		sb.append(". Message: ");
		sb.append(message);
		return sb.toString();
	}

}

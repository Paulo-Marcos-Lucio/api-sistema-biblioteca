package com.devteam.biblioteca.domain.exception;

public abstract class EntidadeEmUsoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public EntidadeEmUsoException(String msg) {
		super(msg);
	}

}

package com.devteam.biblioteca.domain.exception;

public class AutorEmUsoException extends EntidadeEmUsoException {

	
	private static final long serialVersionUID = 1L;

	public AutorEmUsoException(String msg) {
		super(msg);
	}
	
	public AutorEmUsoException(Long id) {
		this(String.format("Autor deste id '%d' está em uso.", id));
	}

}

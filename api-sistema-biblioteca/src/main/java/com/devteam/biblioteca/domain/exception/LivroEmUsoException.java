package com.devteam.biblioteca.domain.exception;

public class LivroEmUsoException extends EntidadeEmUsoException {

	
	private static final long serialVersionUID = 1L;

	public LivroEmUsoException(String msg) {
		super(msg);
	}

	public LivroEmUsoException(Long id) {
		this(String.format("Livro deste id '%d' está em uso.", id));
	}
}

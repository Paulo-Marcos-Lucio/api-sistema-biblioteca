package com.devteam.biblioteca.domain.exception;

public class EmprestimoEmUsoException extends EntidadeEmUsoException {

	
	private static final long serialVersionUID = 1L;

	public EmprestimoEmUsoException(String msg) {
		super(msg);
	}

	public EmprestimoEmUsoException(Long id) {
		this(String.format("Empréstimo deste id %d está em uso.", id));
	}
}

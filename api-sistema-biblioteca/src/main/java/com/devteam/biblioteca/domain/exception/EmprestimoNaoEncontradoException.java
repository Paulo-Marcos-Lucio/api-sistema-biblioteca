package com.devteam.biblioteca.domain.exception;

public class EmprestimoNaoEncontradoException extends EntidadeNaoEncontradaException {


	private static final long serialVersionUID = 1L;

	public EmprestimoNaoEncontradoException(String msg) {
		super(msg);
	}

	
	public EmprestimoNaoEncontradoException(Long id) {
		this(String.format("Empréstimo deste id %d nao encontrado.", id));
	}
}

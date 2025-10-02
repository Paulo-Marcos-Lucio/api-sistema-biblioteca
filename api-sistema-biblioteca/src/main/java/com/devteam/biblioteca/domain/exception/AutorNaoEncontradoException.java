package com.devteam.biblioteca.domain.exception;

public class AutorNaoEncontradoException  extends EntidadeNaoEncontradaException {


	private static final long serialVersionUID = 1L;

	public AutorNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public AutorNaoEncontradoException(Long id) {
		this(String.format("Autor deste id '%d' não encontrado.", id));
	}

}

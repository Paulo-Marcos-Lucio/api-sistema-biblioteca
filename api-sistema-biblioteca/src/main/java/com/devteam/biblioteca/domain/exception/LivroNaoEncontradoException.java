package com.devteam.biblioteca.domain.exception;

public class LivroNaoEncontradoException extends EntidadeNaoEncontradaException {


	private static final long serialVersionUID = 1L;

	public LivroNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public LivroNaoEncontradoException(Long id) {
		this(String.format("Livro deste id '%d' não encontrado.", id));
	}

}

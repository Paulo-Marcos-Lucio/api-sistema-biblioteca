package com.devteam.biblioteca.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public UsuarioNaoEncontradoException(Long id) {
		this(String.format("Usuário deste id '%d' nao encontrado. ", id));
	}

}

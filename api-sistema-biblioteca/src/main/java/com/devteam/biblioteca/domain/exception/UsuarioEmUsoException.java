package com.devteam.biblioteca.domain.exception;

public class UsuarioEmUsoException extends EntidadeEmUsoException {

	private static final long serialVersionUID = 1L;

	public UsuarioEmUsoException(String msg) {
		super(msg);
	}
	
	public UsuarioEmUsoException(Long id) {
		this(String.format("Usuario deste id '%d' está em uso. ", id));
	}

}

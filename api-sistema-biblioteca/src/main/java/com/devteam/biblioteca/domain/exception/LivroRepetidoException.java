package com.devteam.biblioteca.domain.exception;

public class LivroRepetidoException extends NegocioException {


	private static final long serialVersionUID = 1L;

	public LivroRepetidoException(String msg) {
		super("Não é possível adicionar o mesmo livro mais de uma vez no empréstimo");
	}
	

}

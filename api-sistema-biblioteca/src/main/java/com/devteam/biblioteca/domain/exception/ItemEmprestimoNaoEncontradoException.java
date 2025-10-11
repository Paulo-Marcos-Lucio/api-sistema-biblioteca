package com.devteam.biblioteca.domain.exception;

public class ItemEmprestimoNaoEncontradoException extends EntidadeNaoEncontradaException {

	
	private static final long serialVersionUID = 1L;

	public ItemEmprestimoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public ItemEmprestimoNaoEncontradoException(Long id) {
		this(String.format("Item de empréstimo deste id %d nao encontrado.", id));
	}

}

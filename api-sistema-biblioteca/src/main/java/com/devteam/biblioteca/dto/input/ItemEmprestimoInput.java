package com.devteam.biblioteca.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemEmprestimoInput {

	@NotNull
	@PositiveOrZero
	private Integer quantidade;
	
	@Valid
	@NotNull
	private EmprestimoIdInput emprestimo;
	
	@Valid
	@NotNull
	private LivroIdInput livro;
}

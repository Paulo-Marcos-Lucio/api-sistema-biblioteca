package com.devteam.biblioteca.dto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemEmprestimoModel {

	private Long id;
	private Integer quantidade;
	private LivroResumoModel livro;
}

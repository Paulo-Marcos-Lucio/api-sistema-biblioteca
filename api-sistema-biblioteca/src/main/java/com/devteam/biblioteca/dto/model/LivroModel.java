package com.devteam.biblioteca.dto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroModel {

	private Long id;
	private String titulo;
	private String isbn;
	private Integer estoque;
	private AutorResumoModel autor;
}

package com.devteam.biblioteca.dto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroResumoModel {

	private Long id;
	private String titulo;
	private AutorResumoModel autor;
}

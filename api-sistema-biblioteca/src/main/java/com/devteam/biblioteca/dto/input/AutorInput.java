package com.devteam.biblioteca.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorInput {

	@NotBlank(message = "o nome do autor é obrigatório.")
	private String nome;
}

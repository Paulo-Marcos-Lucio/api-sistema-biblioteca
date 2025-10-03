package com.devteam.biblioteca.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LivroInput {

	@NotBlank(message = "A inserção do título deste livro é obrigatória.")
	private String titulo;
	
	@NotBlank(message = "A inserção do ISBN deste livro é obrigatória.")
	private String isbn;
	
	@PositiveOrZero(message = "O estoque precisa no mínimo ter a partir de 0 unidades.")
	@NotNull(message = "A inserção da quantia em estoque deste livro é obrigatória.")
	private Integer estoque;
	
	
	@NotNull(message = "A inserção do Id do autor deste livro é obrigatória.")
	@Valid
	private AutorIdInput autor;
}

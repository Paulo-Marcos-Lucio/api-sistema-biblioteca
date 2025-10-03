package com.devteam.biblioteca.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

	@NotBlank(message = "O nome do usuário não pode estar em branco ou nulo.")
	private String nome;
	
	@NotBlank(message = "O email não pode estar nulo ou em branco.")
	@Email(message = "insira um email válido")
	private String email;
}

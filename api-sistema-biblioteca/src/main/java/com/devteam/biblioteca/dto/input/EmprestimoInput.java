package com.devteam.biblioteca.dto.input;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmprestimoInput {

	@Valid
	@NotNull
	private UsuarioIdInput usuario;
	
	@Valid
	@NotNull
	private List<ItemEmprestimoInput> itensEmprestimo = new ArrayList<>();
}

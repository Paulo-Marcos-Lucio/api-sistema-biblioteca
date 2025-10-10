package com.devteam.biblioteca.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemEmprestimoIdInput {

	@NotNull
	private Long id;
}

package com.devteam.biblioteca.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorIdInput {

	@NotNull
	private Long id;
}

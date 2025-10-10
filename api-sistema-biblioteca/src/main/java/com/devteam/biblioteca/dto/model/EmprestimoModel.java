package com.devteam.biblioteca.dto.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmprestimoModel {

	private Long id;
	private Boolean ativo;
	private LocalDateTime dataCriacao;
	private UsuarioResumoModel usuario;
	private List<ItemEmprestimoModel> itensEmprestimo;
}

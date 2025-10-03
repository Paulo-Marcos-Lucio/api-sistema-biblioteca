package com.devteam.biblioteca.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "tbautor")
public class Autor {

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	private Boolean ativo;
	
	
	public void ativar() {
		this.ativo = true;
	}
	
	public void desativar() {
		this.ativo = false;
	}
	
	
	public Boolean isAtivo() {
		return Boolean.TRUE.equals(this.ativo);
	}
	
	
	public Boolean isInativo() {
		return Boolean.FALSE.equals(this.ativo);
	}
}

package com.devteam.biblioteca.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "tbemprestimo")
public class Emprestimo {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Column(nullable = false)
	private Boolean ativo = true;
	
	@Column(nullable = false)
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	
	@OneToMany(mappedBy = "emprestimo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemEmprestimo> itensEmprestimo = new ArrayList<>();
	
	
	
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
	
	
	public void associarItemEmprestimo(ItemEmprestimo itemEmp) {
		this.itensEmprestimo.add(itemEmp);
		itemEmp.setEmprestimo(this);
	}
	
	
	public void desassociarItemEmprestimo(ItemEmprestimo itemEmp) {
		this.itensEmprestimo.remove(itemEmp);
		itemEmp.setEmprestimo(null);
	}
	
	
	
	
	public Integer calcularQuantidade() {
		return itensEmprestimo.stream()
				.mapToInt(ItemEmprestimo::getQuantidade)
				.sum();
	}
	
	
	
	
	
	
	
	
}

package com.devteam.biblioteca.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devteam.biblioteca.domain.model.Emprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long>{

	//busca todos os empréstimos de um usuário
	List<Emprestimo> findByUsuarioId(Long id);
	
	//busca todos os empréstimos ativos
	List<Emprestimo> findByAtivoTrue();
	
	//busca todos os empréstimos ativos de um usuário
	List<Emprestimo> findByUsuarioIdAndAtivoTrue(Long id);
}

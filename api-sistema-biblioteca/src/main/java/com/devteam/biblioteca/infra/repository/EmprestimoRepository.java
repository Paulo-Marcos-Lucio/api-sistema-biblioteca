package com.devteam.biblioteca.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	
    @Query("SELECT DISTINCT e FROM Emprestimo e " +
            "LEFT JOIN FETCH e.itensEmprestimo i " +
            "LEFT JOIN FETCH i.livro")
     List<Emprestimo> findAllComItens();
    
    
    @Query("SELECT e FROM Emprestimo e " +
            "LEFT JOIN FETCH e.itensEmprestimo i " +
            "LEFT JOIN FETCH i.livro " +
            "WHERE e.id = :id")
     Optional<Emprestimo> findByIdComItens(@Param("id") Long id);

}

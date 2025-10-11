package com.devteam.biblioteca.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devteam.biblioteca.domain.model.ItemEmprestimo;

@Repository
public interface ItemEmprestimoRepository extends JpaRepository<ItemEmprestimo, Long> {

	
		List<ItemEmprestimo> findByEmprestimoId(Long emprestimoId);
	    
	    List<ItemEmprestimo> findByLivroId(Long livroId);
	    
	    Optional<ItemEmprestimo> findByEmprestimoIdAndLivroId(Long emprestimoId, Long livroId);
	    
	    // NOVO: Conta quantos itens um empréstimo tem
	    long countByEmprestimoId(Long emprestimoId);
	    
	    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END " +
	           "FROM ItemEmprestimo i " +
	           "WHERE i.livro.id = :livroId AND i.emprestimo.ativo = true")
	    boolean existsByLivroIdInEmprestimosAtivos(@Param("livroId") Long livroId);
	    
	    @Query("SELECT COALESCE(SUM(i.quantidade), 0) " +
	           "FROM ItemEmprestimo i " +
	           "WHERE i.livro.id = :livroId AND i.emprestimo.ativo = true")
	    Integer somarQuantidadeEmprestadaPorLivro(@Param("livroId") Long livroId);
}

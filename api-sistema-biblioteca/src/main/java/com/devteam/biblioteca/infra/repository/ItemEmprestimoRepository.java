package com.devteam.biblioteca.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devteam.biblioteca.domain.model.ItemEmprestimo;

@Repository
public interface ItemEmprestimoRepository extends JpaRepository<ItemEmprestimo, Long> {

	List<ItemEmprestimo> findAllByEmprestimoId(Long emprestimoId);
}

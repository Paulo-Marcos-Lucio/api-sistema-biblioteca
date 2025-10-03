package com.devteam.biblioteca.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devteam.biblioteca.domain.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{

}

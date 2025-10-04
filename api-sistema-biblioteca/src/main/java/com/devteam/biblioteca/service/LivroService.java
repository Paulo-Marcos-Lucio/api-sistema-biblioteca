package com.devteam.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.devteam.biblioteca.domain.exception.LivroEmUsoException;
import com.devteam.biblioteca.domain.exception.LivroNaoEncontradoException;
import com.devteam.biblioteca.domain.model.Autor;
import com.devteam.biblioteca.domain.model.Livro;
import com.devteam.biblioteca.infra.repository.LivroRepository;

import jakarta.transaction.Transactional;

@Service
public class LivroService {
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private AutorService autorService;
	
	
	@Transactional
	public Livro insert(Livro livro) {
		Long idAutor = livro.getAutor().getId();
		Autor autorDoLivro = autorService.findOrFailById(idAutor);
		
		livro.setAutor(autorDoLivro);
		
		
		return livroRepository.save(livro);
	}
	
	@Transactional
	public void delete(Long id) {
		findOrFailById(id);
		try {
			livroRepository.deleteById(id);
			livroRepository.flush();
		}
		catch(DataIntegrityViolationException ex) {
			throw new LivroEmUsoException(id);
		}
	}

	public Livro findOrFailById(Long id) {
		return livroRepository.findById(id)
				.orElseThrow(() -> new LivroNaoEncontradoException(id));
	}
}

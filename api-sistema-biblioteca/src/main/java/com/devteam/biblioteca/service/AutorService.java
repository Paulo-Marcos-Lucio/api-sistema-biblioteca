package com.devteam.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.devteam.biblioteca.domain.exception.AutorEmUsoException;
import com.devteam.biblioteca.domain.exception.AutorNaoEncontradoException;
import com.devteam.biblioteca.domain.model.Autor;
import com.devteam.biblioteca.infra.repository.AutorRepository;

import jakarta.transaction.Transactional;

@Service
public class AutorService {

	@Autowired
	private AutorRepository autorRepository;
	
	
	@Transactional
	public Autor insert(Autor autor) {
		return autorRepository.save(autor);
	}
	
	
	@Transactional
	public void delete(Long id) {
		findOrFailById(id);
		try {
			autorRepository.deleteById(id);
			autorRepository.flush();
		}
		catch(DataIntegrityViolationException ex) {
			throw new AutorEmUsoException(id);
		}
	}
	
	public Autor findOrFailById(Long id) {
		return autorRepository.findById(id)
				.orElseThrow(() -> new AutorNaoEncontradoException(id));
	}
}

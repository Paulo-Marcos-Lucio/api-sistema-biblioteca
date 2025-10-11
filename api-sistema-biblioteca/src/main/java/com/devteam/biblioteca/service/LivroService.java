package com.devteam.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.devteam.biblioteca.domain.exception.LivroEmUsoException;
import com.devteam.biblioteca.domain.exception.LivroNaoEncontradoException;
import com.devteam.biblioteca.domain.exception.NegocioException;
import com.devteam.biblioteca.domain.model.Autor;
import com.devteam.biblioteca.domain.model.Livro;
import com.devteam.biblioteca.dto.input.LivroInput;
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
		
		
		if(autorDoLivro.isInativo()) {
			throw new NegocioException("Autor do livro não pode estar desativado no sistema.");
		}
		
		if(livroRepository.existsByIsbn(livro.getIsbn())) {
			throw new NegocioException("Livro deste ISBN: " + livro.getIsbn() + " já existe.");
		}
		
		livro.setAutor(autorDoLivro);
		
		
		return livroRepository.save(livro);
	}
	
	
	
	
	@Transactional
	public Livro update(Long id, LivroInput livroInput) {
	    // Busca o livro existente
	    Livro livroExistente = findOrFailById(id);
	    
	    // Guarda o ISBN original
	    String isbnOriginal = livroExistente.getIsbn();
	    
	    // VALIDAÇÃO 1: Se mudou o ISBN, verifica se o novo já existe
	    if (!isbnOriginal.equals(livroInput.getIsbn())) {
	        if (livroRepository.existsByIsbn(livroInput.getIsbn())) {
	            throw new NegocioException(
	                String.format("Já existe outro livro cadastrado com o ISBN %s", 
	                livroInput.getIsbn())
	            );
	        }
	    }
	    
	    // VALIDAÇÃO 2: Verifica se o autor existe e está ativo
	    Autor autor = autorService.findOrFailById(livroInput.getAutor().getId());
	    
	    if (!autor.getAtivo()) {
	        throw new NegocioException("Não é possível associar livros a autores inativos");
	    }
	    
	    // Copia as propriedades do Input para o Domain
	    livroExistente.setTitulo(livroInput.getTitulo());
	    livroExistente.setIsbn(livroInput.getIsbn());
	    livroExistente.setEstoque(livroInput.getEstoque());
	    
	    livroExistente.setAutor(autor);
	    
	    // Salva as alterações
	    return livroRepository.save(livroExistente);
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

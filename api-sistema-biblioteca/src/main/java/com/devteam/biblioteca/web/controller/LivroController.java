package com.devteam.biblioteca.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devteam.biblioteca.domain.exception.AutorNaoEncontradoException;
import com.devteam.biblioteca.domain.exception.NegocioException;
import com.devteam.biblioteca.domain.model.Livro;
import com.devteam.biblioteca.dto.assembler.LivroModelAssembler;
import com.devteam.biblioteca.dto.disassembler.LivroInputDisassembler;
import com.devteam.biblioteca.dto.input.LivroInput;
import com.devteam.biblioteca.dto.model.LivroModel;
import com.devteam.biblioteca.infra.repository.LivroRepository;
import com.devteam.biblioteca.service.LivroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {

	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private LivroService livroService;
	
	@Autowired
	private LivroModelAssembler livroModelAssb;
	
	@Autowired
	private LivroInputDisassembler livroInputDissb;
	
	
	
	
	@GetMapping
	public List<LivroModel> findAll() {
		List<Livro> livrosDomain = livroRepository.findAll();
		return livroModelAssb.listEntityToListModel(livrosDomain);
	}
	
	
	
	@GetMapping("/{id}")
	public LivroModel findById(@PathVariable Long id) {
		Livro livroDomain = livroService.findOrFailById(id);
		return livroModelAssb.entityToModel(livroDomain);
	}
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public LivroModel insert(@RequestBody @Valid LivroInput livroInput) {
		try {
			Livro livroDomain = livroInputDissb.inputToDomainObj(livroInput);
			return livroModelAssb.entityToModel(livroService.insert(livroDomain));
		}
		catch(AutorNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}
	
	

	@PutMapping("/{id}")
	public LivroModel update(@PathVariable Long id, @RequestBody @Valid LivroInput livroInput) {
	    try {
	        // Apenas valida se existe, mas não usa o resultado
	        livroService.findOrFailById(id);
	        
	        // Chama o service passando ID e Input separadamente
	        Livro livroAtualizado = livroService.update(id, livroInput);
	        
	        return livroModelAssb.entityToModel(livroAtualizado);
	        
	    } catch(AutorNaoEncontradoException ex) {
	        throw new NegocioException(ex.getMessage(), ex);
	    }
	}
	
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		livroService.delete(id);
	}
}
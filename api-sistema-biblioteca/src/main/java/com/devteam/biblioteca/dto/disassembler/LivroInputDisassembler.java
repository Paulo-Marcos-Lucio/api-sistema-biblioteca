package com.devteam.biblioteca.dto.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devteam.biblioteca.domain.model.Autor;
import com.devteam.biblioteca.domain.model.Livro;
import com.devteam.biblioteca.dto.input.LivroInput;

@Component
public class LivroInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public Livro inputToDomainObj(LivroInput livroInput) {
		return modelMapper.map(livroInput, Livro.class);
	}
	
	
	public void copyInputToDomainProperties(LivroInput livroInput, Livro livroDomain) {
		// IMPORTANTE: Desvincula o autor antigo para evitar erro do Hibernate
        livroDomain.setAutor(new Autor());
        
		modelMapper.map(livroInput, livroDomain);
	}
}

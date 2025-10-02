package com.devteam.biblioteca.dto.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devteam.biblioteca.domain.model.Autor;
import com.devteam.biblioteca.dto.input.AutorInput;

@Component
public class AutorInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Autor inputToDomainObj(AutorInput autorInput) {
		return modelMapper.map(autorInput, Autor.class);
	}
	
	
	public void copyInputToDomainProperties(AutorInput autorInput, Autor autorDomain) {
		modelMapper.map(autorInput, autorDomain);
	}
}

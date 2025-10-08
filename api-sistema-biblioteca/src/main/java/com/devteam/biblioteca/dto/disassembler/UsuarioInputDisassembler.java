package com.devteam.biblioteca.dto.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devteam.biblioteca.domain.model.Usuario;
import com.devteam.biblioteca.dto.input.UsuarioInput;

@Component
public class UsuarioInputDisassembler {

	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public Usuario inputToDomainObj(UsuarioInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	
	public void copyInputToDomainProperties
			(UsuarioInput usuarioInput, Usuario usuarioDomain) {
		
		modelMapper.map(usuarioInput, usuarioDomain);
		
	}
	
	
}

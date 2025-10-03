package com.devteam.biblioteca.dto.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devteam.biblioteca.domain.model.Usuario;
import com.devteam.biblioteca.dto.model.UsuarioModel;

@Component
public class UsuarioModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public UsuarioModel entityToModel(Usuario usuarioDomain) {
		return modelMapper.map(usuarioDomain, UsuarioModel.class);
	}
	
	
	public List<UsuarioModel> listEntityToListModel(List<Usuario> usuariosDomain) {
		return usuariosDomain
				.stream()
				.map(usuarioDomain -> entityToModel(usuarioDomain))
				.collect(Collectors.toList());
	}
}

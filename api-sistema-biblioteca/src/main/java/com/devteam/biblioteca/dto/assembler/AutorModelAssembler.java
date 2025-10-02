package com.devteam.biblioteca.dto.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devteam.biblioteca.domain.model.Autor;
import com.devteam.biblioteca.dto.model.AutorModel;

@Component
public class AutorModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public AutorModel entityToModel(Autor autor) {
		return modelMapper.map(autor, AutorModel.class);
	}
	
	public List<AutorModel> listEntityToListModel(List<Autor> autoresDomain) {
		return autoresDomain.stream()
				.map(autorDomain -> entityToModel(autorDomain))
				.collect(Collectors.toList());
	}
}

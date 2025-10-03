package com.devteam.biblioteca.dto.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devteam.biblioteca.domain.model.Livro;
import com.devteam.biblioteca.dto.model.LivroModel;

@Component
public class LivroModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public LivroModel entityToModel(Livro livroDomain) {
		return modelMapper.map(livroDomain, LivroModel.class);
	}
	
	public List<LivroModel> listEntityToListModel(List<Livro> livrosDomain) {
		return livrosDomain.stream()
				.map(livroDomain -> entityToModel(livroDomain))
				.collect(Collectors.toList());
	}
}

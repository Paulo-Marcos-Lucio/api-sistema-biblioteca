package com.devteam.biblioteca.dto.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devteam.biblioteca.domain.model.Emprestimo;
import com.devteam.biblioteca.dto.model.EmprestimoModel;

@Component
public class EmprestimoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	
	public EmprestimoModel entityToModel(Emprestimo emp) {
		return modelMapper.map(emp, EmprestimoModel.class);
	}
	
	
	public List<EmprestimoModel> listEntityToListModel(List<Emprestimo> empsBd) {
		return empsBd.stream()
				.map(this::entityToModel)
				.collect(Collectors.toList());
	}
}

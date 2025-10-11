package com.devteam.biblioteca.dto.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devteam.biblioteca.domain.model.ItemEmprestimo;
import com.devteam.biblioteca.dto.model.ItemEmprestimoModel;

@Component
public class ItemEmprestimoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public ItemEmprestimoModel entityToModel(ItemEmprestimo itemEmp) {
		return modelMapper.map(itemEmp, ItemEmprestimoModel.class);
	}
	
	
	public List<ItemEmprestimoModel> listEntityToListModel(List<ItemEmprestimo> itensEmpBd) {
		return itensEmpBd.stream()
				.map(this::entityToModel)
				.collect(Collectors.toList());
	}
}

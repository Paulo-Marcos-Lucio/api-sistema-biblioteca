package com.devteam.biblioteca.dto.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devteam.biblioteca.domain.model.Emprestimo;
import com.devteam.biblioteca.domain.model.ItemEmprestimo;
import com.devteam.biblioteca.domain.model.Livro;
import com.devteam.biblioteca.dto.input.ItemEmprestimoInput;

@Component
public class ItemEmprestimoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public ItemEmprestimo inputToDomainObj(ItemEmprestimoInput itemEmpInp) {
		return modelMapper.map(itemEmpInp, ItemEmprestimo.class);
	}
	
	public void copyPropertiesInpToDomain(ItemEmprestimoInput itemEmpInp, ItemEmprestimo itemEmp) {
		itemEmp.setLivro(new Livro());
		itemEmp.setEmprestimo(new Emprestimo());
		modelMapper.map(itemEmpInp, itemEmp);
	}
}

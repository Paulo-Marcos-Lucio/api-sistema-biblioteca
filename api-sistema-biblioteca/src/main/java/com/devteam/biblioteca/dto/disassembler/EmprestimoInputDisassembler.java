package com.devteam.biblioteca.dto.disassembler;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devteam.biblioteca.domain.model.Emprestimo;
import com.devteam.biblioteca.domain.model.ItemEmprestimo;
import com.devteam.biblioteca.domain.model.Usuario;
import com.devteam.biblioteca.dto.input.EmprestimoInput;

@Component
public class EmprestimoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public Emprestimo inputToDomainObj(EmprestimoInput empInp) {
		return modelMapper.map(empInp, Emprestimo.class);
	}
	
	public void copyPorpertiesInpToDomain(EmprestimoInput empInp, Emprestimo emp) {
		emp.setItensEmprestimo(new ArrayList<ItemEmprestimo>());
		emp.setUsuario(new Usuario());
		modelMapper.map(empInp, emp);
	}
}

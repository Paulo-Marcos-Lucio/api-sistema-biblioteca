package com.devteam.biblioteca.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devteam.biblioteca.domain.exception.EntidadeNaoEncontradaException;
import com.devteam.biblioteca.domain.exception.NegocioException;
import com.devteam.biblioteca.domain.model.Autor;
import com.devteam.biblioteca.dto.assembler.AutorModelAssembler;
import com.devteam.biblioteca.dto.disassembler.AutorInputDisassembler;
import com.devteam.biblioteca.dto.input.AutorInput;
import com.devteam.biblioteca.dto.model.AutorModel;
import com.devteam.biblioteca.infra.repository.AutorRepository;
import com.devteam.biblioteca.service.AutorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/autores")
public class AutorController {
	
	
	@Autowired
	private AutorRepository autorRep;
	
	@Autowired
	private AutorService autorServ;
	
	@Autowired
	private AutorModelAssembler autorModelAssb;
	
	@Autowired
	private AutorInputDisassembler autorInputDissb;
	
	
	
	
	@GetMapping
	public List<AutorModel> findAll() {
		List<Autor> autoresDomain = autorRep.findAll();
		return autorModelAssb.listEntityToListModel(autoresDomain);
	}
	
	
	
	@GetMapping("/{id}")
	public AutorModel findById(@PathVariable Long id) {
		Autor autorDomain = autorServ.findOrFailById(id);
		return autorModelAssb.entityToModel(autorDomain);
	}
	
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public AutorModel insert(@RequestBody @Valid AutorInput autorInput) {
		Autor autorDomain = autorInputDissb.inputToDomainObj(autorInput);
		return autorModelAssb.entityToModel(autorServ.insert(autorDomain));
	}
	
	
	
	@PutMapping("/{id}")
	public AutorModel update(@PathVariable Long id, @RequestBody @Valid AutorInput autorInput) {
		try {
		Autor autorDomain = autorServ.findOrFailById(id);
		autorInputDissb.copyInputToDomainProperties(autorInput, autorDomain);
		return autorModelAssb.entityToModel(autorServ.insert(autorDomain));
		}
		catch(EntidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}
	
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		autorServ.delete(id);
	}
	
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/ativar/{id}")
	public void ativar(@PathVariable Long id) {
		autorServ.ativar(id);
	}
	
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/desativar/{id}")
	public void desativar(@PathVariable Long id) {
		autorServ.desativar(id);
	}
	

}












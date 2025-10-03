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

import com.devteam.biblioteca.domain.model.Usuario;
import com.devteam.biblioteca.dto.assembler.UsuarioModelAssembler;
import com.devteam.biblioteca.dto.disassembler.UsuarioInputDisassembler;
import com.devteam.biblioteca.dto.input.UsuarioInput;
import com.devteam.biblioteca.dto.model.UsuarioModel;
import com.devteam.biblioteca.infra.repository.UsuarioRepository;
import com.devteam.biblioteca.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioModelAssembler usuarioModAssb;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDIssb;
	
	
	
	
	@GetMapping
	public List<UsuarioModel> findAll() {
		List<Usuario> usuariosDomain = usuarioRepository.findAll();
		return usuarioModAssb.listEntityToListModel(usuariosDomain);
	}
	
	
	
	@GetMapping("/{id}")
	public UsuarioModel findById(@PathVariable Long id) {
		Usuario usuarioDomain = usuarioService.findOrFailById(id);
		return usuarioModAssb.entityToModel(usuarioDomain);
	}
	
	

	@PostMapping
	public UsuarioModel insert(@RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioDomain = usuarioInputDIssb.inputToDomainObj(usuarioInput);
		usuarioService.insert(usuarioDomain);
		return usuarioModAssb.entityToModel(usuarioDomain);
	}
	
	
	
	@PutMapping("/{id}")
	public UsuarioModel update(@PathVariable Long id, 
					@RequestBody @Valid UsuarioInput usuarioInput) {
		
		usuarioService.findOrFailById(id);
		Usuario usuarioAtualizado = usuarioService.update(id, usuarioInput);
		return usuarioModAssb.entityToModel(usuarioAtualizado);
		
	}
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		usuarioService.delete(id);
	}
	
	
}















package com.devteam.biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.devteam.biblioteca.domain.exception.NegocioException;
import com.devteam.biblioteca.domain.exception.UsuarioEmUsoException;
import com.devteam.biblioteca.domain.exception.UsuarioNaoEncontradoException;
import com.devteam.biblioteca.domain.model.Usuario;
import com.devteam.biblioteca.dto.input.UsuarioInput;
import com.devteam.biblioteca.infra.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	
	@Transactional
	public Usuario insert(Usuario usuario) {
		
		//verifica se o email digitado na inserção já existe
		if(usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new NegocioException
				("Já existe um usuário cadastrado com este email. "
							+ usuario.getEmail());
		}
		
		return usuarioRepository.save(usuario);
	}
	
	
	
	@Transactional
	public Usuario update(Long id, UsuarioInput usuarioInput) {
		Usuario usuarioExistente = findOrFailById(id);
		
		String emailOriginal = usuarioExistente.getEmail();
		String emailAtualizado = usuarioInput.getEmail();
		
		
		//verifica se o email foi atualizado, e se o email atualizado já existe
		if(!emailOriginal.equals(emailAtualizado)) {
			if(usuarioRepository.existsByEmail(emailAtualizado)) {
				throw new NegocioException
						("Já existe um usuário cadastrado com este email." + emailAtualizado);
			}
		}
		
		usuarioExistente.setNome(usuarioInput.getNome());
		usuarioExistente.setEmail(usuarioInput.getEmail());
		
		return usuarioRepository.save(usuarioExistente);
		
	}
	
	
	
	
	@Transactional
	public void delete(Long id) {
		findOrFailById(id);
		try {
			usuarioRepository.deleteById(id);
			usuarioRepository.flush();
		}
		catch(DataIntegrityViolationException ex) {
			throw new UsuarioEmUsoException(id);
		}
	}
	
	
	
	public Usuario findOrFailById(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
}

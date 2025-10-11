package com.devteam.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devteam.biblioteca.domain.exception.EmprestimoNaoEncontradoException;
import com.devteam.biblioteca.domain.exception.ItemEmprestimoNaoEncontradoException;
import com.devteam.biblioteca.domain.exception.NegocioException;
import com.devteam.biblioteca.domain.model.Emprestimo;
import com.devteam.biblioteca.domain.model.ItemEmprestimo;
import com.devteam.biblioteca.domain.model.Livro;
import com.devteam.biblioteca.dto.input.ItemEmprestimoInput;
import com.devteam.biblioteca.infra.repository.ItemEmprestimoRepository;
import com.devteam.biblioteca.infra.repository.LivroRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemEmprestimoService {

	@Autowired
	private ItemEmprestimoRepository itemEmpRep;
	
	@Autowired
	private EmprestimoService empServ;
	
	@Autowired
	private LivroService livroServ;
	
	@Autowired
	private LivroRepository livroRep;
	
	
	
	@Transactional
	public ItemEmprestimo insert(Long emprestimoId, ItemEmprestimoInput itemEmpInp) {
		
		//verifica a existencia do empréstimo
		Emprestimo emprestimo = empServ.findOrFailById(emprestimoId);
		
		
		//verifica se o empréstimo está ativo
		if(emprestimo.isInativo()) {
			throw new NegocioException("Não é possível adcionar item de empréstimo a um empréstimo inativo.");
		}
		
		
		//verifica se a quantidade inserida é válida
		if (itemEmpInp.getQuantidade() <= 0) {
            throw new NegocioException(
                "A quantidade de cada item deve ser maior que zero");
        }
		
		Long idLivro = itemEmpInp.getLivro().getId();
		Livro livroDoItem = livroServ.findOrFailById(idLivro);
		
		//verifica se a quantidade de items requeridos de empréstimo é compativel com a quantidade de estoque do livro
		if(itemEmpInp.getQuantidade() > livroDoItem.getEstoque()) {
			throw new NegocioException("Estoque deste livro insuficiente para a quantidade desejada. "
					+ "\nEstoque do livro disponível: " + livroDoItem.getEstoque()
					+"\nQuantidade desejada: " + itemEmpInp.getQuantidade());
		}
		
		//verifica se o livro já existe no empréstimo
		Optional<ItemEmprestimo> itemExistente = itemEmpRep.findByEmprestimoIdAndLivroId(emprestimoId, idLivro);
		
		if(itemExistente.isPresent()) {
			throw new NegocioException(String.format("O livro %s já está neste empréstimo. ", livroDoItem.getTitulo()));
		}

		
		//cria novo item empréstimo
		ItemEmprestimo novoItemEmp = new ItemEmprestimo();
		novoItemEmp.setEmprestimo(emprestimo);
		novoItemEmp.setLivro(livroDoItem);
		novoItemEmp.setQuantidade(itemEmpInp.getQuantidade());
		
		
		//salva novo item empréstimo
		ItemEmprestimo itemSalvo = itemEmpRep.saveAndFlush(novoItemEmp);
		
		
		//atualiza o estoque do livro
				int novoEstoque = livroDoItem.getEstoque() - itemEmpInp.getQuantidade();
				livroDoItem.setEstoque(novoEstoque);
				livroRep.saveAndFlush(livroDoItem);
	
		
		return itemSalvo;
		
	}
	

	
	 public List<ItemEmprestimo> findByEmprestimoId(Long emprestimoId) {
		 	empServ.findOrFailById(emprestimoId);
	        return itemEmpRep.findByEmprestimoId(emprestimoId);
	 }
	 
	 
	 public ItemEmprestimo findByIdAndEmprestimoId(Long itemId, Long emprestimoId) {
	        empServ.findOrFailById(emprestimoId);
	        ItemEmprestimo item = findOrFailById(itemId);
	        
	        // Valida se o item pertence ao empréstimo
	        if (!item.getEmprestimo().getId().equals(emprestimoId)) {
	            throw new NegocioException(
	                String.format("O item %d não pertence ao empréstimo %d", itemId, emprestimoId)
	            );
	        }
	        
	        return item;
	    }
	
	
	
	public ItemEmprestimo findOrFailById(Long id) {
		return itemEmpRep.findById(id)
				.orElseThrow(() -> new ItemEmprestimoNaoEncontradoException(id));
	}
}

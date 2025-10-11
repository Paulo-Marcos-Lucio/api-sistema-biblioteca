package com.devteam.biblioteca.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devteam.biblioteca.domain.exception.LivroNaoEncontradoException;
import com.devteam.biblioteca.domain.exception.NegocioException;
import com.devteam.biblioteca.domain.model.ItemEmprestimo;
import com.devteam.biblioteca.dto.assembler.ItemEmprestimoModelAssembler;
import com.devteam.biblioteca.dto.disassembler.ItemEmprestimoInputDisassembler;
import com.devteam.biblioteca.dto.input.ItemEmprestimoInput;
import com.devteam.biblioteca.dto.model.ItemEmprestimoModel;
import com.devteam.biblioteca.service.ItemEmprestimoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/emprestimos/{emprestimoId}/itens")
public class ItemEmprestimoController {
    
    @Autowired
    private ItemEmprestimoService itemEmprestimoService;
    
    @Autowired
    private ItemEmprestimoInputDisassembler itemEmprestimoInputDissb;
    
    @Autowired
    private ItemEmprestimoModelAssembler itemEmprestimoModelAssb;
    
    
   
    @GetMapping
    public List<ItemEmprestimoModel> findAll(@PathVariable Long emprestimoId) {
        List<ItemEmprestimo> itens = itemEmprestimoService.findByEmprestimoId(emprestimoId);
        return itemEmprestimoModelAssb.listEntityToListModel(itens);
    }
    
    
    
    @GetMapping("/{itemId}")
    public ItemEmprestimoModel findById(@PathVariable Long emprestimoId, @PathVariable Long itemId) {
        ItemEmprestimo item = itemEmprestimoService.findByIdAndEmprestimoId(itemId, emprestimoId);
        return itemEmprestimoModelAssb.entityToModel(item);
    }
    
    
   
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemEmprestimoModel adicionarItem(@PathVariable Long emprestimoId, @RequestBody @Valid ItemEmprestimoInput itemInput) {
        try {
            ItemEmprestimo item = itemEmprestimoService.insert(emprestimoId, itemInput);
            return itemEmprestimoModelAssb.entityToModel(item);
            
        } catch (LivroNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
    
  
}
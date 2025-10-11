package com.devteam.biblioteca.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devteam.biblioteca.domain.exception.LivroNaoEncontradoException;
import com.devteam.biblioteca.domain.exception.NegocioException;
import com.devteam.biblioteca.domain.exception.UsuarioNaoEncontradoException;
import com.devteam.biblioteca.domain.model.Emprestimo;
import com.devteam.biblioteca.dto.assembler.EmprestimoModelAssembler;
import com.devteam.biblioteca.dto.disassembler.EmprestimoInputDisassembler;
import com.devteam.biblioteca.dto.input.EmprestimoInput;
import com.devteam.biblioteca.dto.model.EmprestimoModel;
import com.devteam.biblioteca.service.EmprestimoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
    
    @Autowired
    private EmprestimoService emprestimoService;
    
    @Autowired
    private EmprestimoInputDisassembler emprestimoInputDissb;
    
    @Autowired
    private EmprestimoModelAssembler emprestimoModelAssb;
    
    
    
    
    @GetMapping
    public List<EmprestimoModel> findAll() {
        List<Emprestimo> emprestimos = emprestimoService.findAllEmprestimos();
        return emprestimoModelAssb.listEntityToListModel(emprestimos);
    }
    
    
    
    @GetMapping("/{id}")
    public EmprestimoModel findById(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoService.findOrFailById(id);
        return emprestimoModelAssb.entityToModel(emprestimo);
    }
    
    
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmprestimoModel create(@RequestBody @Valid EmprestimoInput emprestimoInput) {
        try {
            Emprestimo emprestimo = emprestimoInputDissb.inputToDomainObj(emprestimoInput);
            Emprestimo emprestimoSalvo = emprestimoService.insert(emprestimo);
            
            // ✅ CRÍTICO: Recarrega o empréstimo do banco com os itens
            emprestimoSalvo = emprestimoService.findByIdComItens(emprestimoSalvo.getId());
            
            return emprestimoModelAssb.entityToModel(emprestimoSalvo);
            
        } catch (UsuarioNaoEncontradoException | LivroNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
    
    
    
    @PutMapping("/{id}/finalizar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizarEmprestimo(@PathVariable Long id) {
        emprestimoService.finalizarEmprestimo(id);
    }
    
    
    
    @GetMapping("/usuario/{usuarioId}")
    public List<EmprestimoModel> findByUsuario(@PathVariable Long usuarioId) {
        List<Emprestimo> emprestimos = emprestimoService.findAllEmprestimosByUsuarioId(usuarioId);
        return emprestimoModelAssb.listEntityToListModel(emprestimos);
    }
    
    
    
    @GetMapping("/usuario/{usuarioId}/ativos")
    public List<EmprestimoModel> findByUsuarioAtivos(@PathVariable Long usuarioId) {
        List<Emprestimo> emprestimos = emprestimoService.findByUsuarioIdAtivos(usuarioId);
        
        // Força carregamento dos itens
        emprestimos.forEach(e -> e.getItensEmprestimo().size());
        
        return emprestimoModelAssb.listEntityToListModel(emprestimos);
    }
}
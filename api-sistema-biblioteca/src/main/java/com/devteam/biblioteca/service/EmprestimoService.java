package com.devteam.biblioteca.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devteam.biblioteca.domain.exception.EmprestimoNaoEncontradoException;
import com.devteam.biblioteca.domain.exception.NegocioException;
import com.devteam.biblioteca.domain.model.Emprestimo;
import com.devteam.biblioteca.domain.model.ItemEmprestimo;
import com.devteam.biblioteca.domain.model.Livro;
import com.devteam.biblioteca.domain.model.Usuario;
import com.devteam.biblioteca.infra.repository.EmprestimoRepository;
import com.devteam.biblioteca.infra.repository.ItemEmprestimoRepository;
import com.devteam.biblioteca.infra.repository.LivroRepository;

import jakarta.transaction.Transactional;

@Service
public class EmprestimoService {
	
	@Autowired
    private EmprestimoRepository emprestimoRepository;
    
    @Autowired
    private ItemEmprestimoRepository itemEmpRep;
    
    @Autowired
    private LivroService livroServ;
    
    @Autowired
    private LivroRepository livroRep;
    
    @Autowired
    private UsuarioService userServ;
    
    
    
    @Transactional
    public Emprestimo insert(Emprestimo emprestimo) {
        //BUSCA USUÁRIO NO BANCO E VERIFICA SE EXISTE
        Usuario usuario = userServ.findOrFailById(emprestimo.getUsuario().getId());

        //VERIFICA SE ESTÁ ATIVO
        if(!usuario.getAtivo()) {
            throw new NegocioException("Não é possivel emprestar para usuários inativos");
        }

        //RELACIONA USUÁRIO COM EMPRÉSTIMO
        emprestimo.setUsuario(usuario);

        //VERIFICA SE O EMPRÉSTIMO TERÁ PELO MENOS 1 ITEM EMPRÉSTIMO RELACIONADO
        if(emprestimo.getItensEmprestimo() == null || emprestimo.getItensEmprestimo().isEmpty()) {
            throw new NegocioException("O empréstimo precisa ter pelo menos 1 item.");
        }

        //VALIDA CADA ITEM DO EMPRÉSTIMO: LIVRO
        for(ItemEmprestimo item : emprestimo.getItensEmprestimo()) {

            //BUSCA O LIVRO DO ITEM E VERIFICA SE EXISTE
            Livro livro = livroServ.findOrFailById(item.getLivro().getId());

           

            //VERIFICA SE O LIVRO POSSUI ESTOQUE SUFICIENTE PARA A QUANTIDADE SOLICITADA
            if(livro.getEstoque() < item.getQuantidade()) {
                throw new NegocioException(
                    String.format("Estoque indisponivel para o livro '%s', "
                        + "Disponível: '%d' "
                        + "Solicitado: '%d'", 
                        livro.getTitulo(), livro.getEstoque(), item.getQuantidade())
                );
            }

            //VERIFICA SE A QUANTIDADE SOLICITADA É PELO MENOS POSITIVA A PARTIR DE 1
            if (item.getQuantidade() <= 0) {
                throw new NegocioException(
                    "A quantidade de cada item deve ser maior que zero");
            }

            //RELACIONA O LIVRO AO ITEM
            item.setLivro(livro);
            
            // ✅ IMPORTANTE: Garante que o item conhece o empréstimo
            item.setEmprestimo(emprestimo);
        }

        // ========== VALIDAÇÃO: Livros Duplicados ==========
        Set<Long> livrosIds = emprestimo.getItensEmprestimo().stream()
            .map(item -> item.getLivro().getId())
            .collect(Collectors.toSet());

        if (livrosIds.size() < emprestimo.getItensEmprestimo().size()) {
            throw new NegocioException(
                "Não é possível adicionar o mesmo livro mais de uma vez no empréstimo");
        }

        // ========== SALVAR ==========
        Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);
        
        // ✅ IMPORTANTE: Força o flush para garantir que tudo foi salvo
        emprestimoRepository.flush();

        // ========== ATUALIZAR ESTOQUE ==========
        emprestimoSalvo.getItensEmprestimo().forEach(item -> {
            Livro livro = item.getLivro();
            int novoEstoque = livro.getEstoque() - item.getQuantidade();
            livro.setEstoque(novoEstoque);
            livroRep.save(livro);
        });
        
        // ✅ IMPORTANTE: Flush novamente após atualizar estoques
        livroRep.flush();

        return emprestimoSalvo;
    }
    
    
    @Transactional
   public List<Emprestimo> findAllEmprestimos() {
    	return emprestimoRepository.findAllComItens();
    }
    
    @Transactional
    public Emprestimo findByIdComItens(Long emprestimoid) {
    	return emprestimoRepository.findByIdComItens(emprestimoid)
    			.orElseThrow(() -> new EmprestimoNaoEncontradoException(emprestimoid));
    }
    
    @Transactional
    public void finalizarEmprestimo(Long id) {
        Emprestimo emprestimo = findOrFailById(id);
        
        if (!emprestimo.isAtivo()) {
            throw new NegocioException("Este empréstimo já está finalizado");
        }
        
        // Devolve os livros ao estoque
        emprestimo.getItensEmprestimo().forEach(item -> {
            Livro livro = item.getLivro();
            livro.setEstoque(livro.getEstoque() + item.getQuantidade());
            livroServ.insert(livro);
        });
        
        // Inativa o empréstimo
        emprestimo.desativar();
        emprestimoRepository.save(emprestimo);
    }

    
    
    public List<Emprestimo> findAll() {
    	return emprestimoRepository.findAll();
    }
    
    
    public List<Emprestimo> findAllEmprestimosAtivos() {
    	return emprestimoRepository.findByAtivoTrue();
    }
    
    
    public List<Emprestimo> findAllEmprestimosByUsuarioId(Long usuarioId) {
    	userServ.findOrFailById(usuarioId);
    	return emprestimoRepository.findByUsuarioId(usuarioId);
    }
    
    
    public List<Emprestimo> findByUsuarioIdAtivos(Long usuarioId) {
        userServ.findOrFailById(usuarioId);
        return emprestimoRepository.findByUsuarioIdAndAtivoTrue(usuarioId);
    }
    
    
    
    public Emprestimo findOrFailById(Long id) {
        return emprestimoRepository.findById(id)
            .orElseThrow(() -> new EmprestimoNaoEncontradoException(id));
    }
    
    
    
    
}

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

import jakarta.transaction.Transactional;

@Service
public class EmprestimoService {
	
	@Autowired
    private EmprestimoRepository emprestimoRepository;
    
    @Autowired
    private ItemEmprestimoRepository itemEmprestimoRepository;
    
    @Autowired
    private LivroService livroServ;
    
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
    	if(emprestimo.getItensEmprestimo() == null 
    			|| emprestimo.getItensEmprestimo().isEmpty()) {
    		
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
    							+ "Solicitado:  '%d", livro.getTitulo(), livro.getEstoque(), item.getQuantidade())
    					);
    		}
    		
    		
    		//VERIFICA SE A QUANTIDADE SOLICITADO É PELO MENOS POSITIVA A PARTIR DE 0
    		 if (item.getQuantidade() <= 0) {
                 throw new NegocioException(
                     "A quantidade de cada item deve ser maior que zero");
             }
    		 
    		 //RELACIONA O LIVRO AO ITEM
    		 item.setLivro(livro);
    	}
    	
    	

		// ========== VALIDAÇÃO 3: Livros Duplicados ==========

		// Cria um Set com os IDs dos livros (Set não aceita duplicatas)
		Set<Long> livrosIds = emprestimo.getItensEmprestimo().stream().map(item -> item.getLivro().getId())
				.collect(Collectors.toSet());

		// Se o Set ficou menor que a lista, tinha duplicata
		if (livrosIds.size() < emprestimo.getItensEmprestimo().size()) {
			throw new NegocioException("Não é possível adicionar o mesmo livro mais de uma vez no empréstimo");
		}

		// ========== SALVAR ==========

		// Salva o empréstimo (os itens são salvos automaticamente por causa do cascade)
		Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);

		// ========== ATUALIZAR ESTOQUE ==========

		// Para cada item, diminui o estoque do livro
		emprestimoSalvo.getItensEmprestimo().forEach(item -> {
			Livro livro = item.getLivro();

			// Calcula novo estoque
			int novoEstoque = livro.getEstoque() - item.getQuantidade();

			// Atualiza o estoque
			livro.setEstoque(novoEstoque);

			// Salva o livro com estoque atualizado
			livroServ.insert(livro);
		});

		return emprestimoSalvo;
    	
    }

    
    private List<Emprestimo> findAll() {
    	return emprestimoRepository.findAll();
    }
    
    
    private List<Emprestimo> findAllEmprestimosAtivos() {
    	return emprestimoRepository.findByAtivoTrue();
    }
    
    
    private List<Emprestimo> findAllEmprestimosByUsuarioId(Long usuarioId) {
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

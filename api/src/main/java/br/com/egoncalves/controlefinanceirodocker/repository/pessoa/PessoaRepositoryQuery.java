/**
 *
 */
package br.com.egoncalves.controlefinanceirodocker.repository.pessoa;

import br.com.egoncalves.controlefinanceirodocker.model.Pessoa;
import br.com.egoncalves.controlefinanceirodocker.repository.filter.PessoaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Esmael
 */
public interface PessoaRepositoryQuery {
    public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);

}

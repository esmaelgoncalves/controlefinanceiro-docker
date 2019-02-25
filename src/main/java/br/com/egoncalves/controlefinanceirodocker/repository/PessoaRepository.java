/**
 *
 */
package br.com.egoncalves.controlefinanceirodocker.repository;

import br.com.egoncalves.controlefinanceirodocker.model.Pessoa;
import br.com.egoncalves.controlefinanceirodocker.repository.pessoa.PessoaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Esmael
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

}

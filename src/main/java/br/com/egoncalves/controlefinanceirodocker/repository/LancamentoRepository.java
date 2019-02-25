/**
 *
 */
package br.com.egoncalves.controlefinanceirodocker.repository;

import br.com.egoncalves.controlefinanceirodocker.model.Lancamento;
import br.com.egoncalves.controlefinanceirodocker.repository.lancamento.LancamentoRespositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Esmael
 */
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRespositoryQuery {

}

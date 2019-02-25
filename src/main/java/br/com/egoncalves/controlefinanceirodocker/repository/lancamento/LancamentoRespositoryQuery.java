/**
 *
 */
package br.com.egoncalves.controlefinanceirodocker.repository.lancamento;

import br.com.egoncalves.controlefinanceirodocker.model.Lancamento;
import br.com.egoncalves.controlefinanceirodocker.model.projection.ResumoLancamento;
import br.com.egoncalves.controlefinanceirodocker.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Esmael
 */
public interface LancamentoRespositoryQuery {
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}

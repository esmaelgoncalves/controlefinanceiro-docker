/**
 *
 */
package br.com.egoncalves.controlefinanceirodocker.resource;


import br.com.egoncalves.controlefinanceirodocker.event.RecursoCriadoEvent;
import br.com.egoncalves.controlefinanceirodocker.exceptionhandler.ControleFinanceiroExceptionHandler;
import br.com.egoncalves.controlefinanceirodocker.model.Lancamento;
import br.com.egoncalves.controlefinanceirodocker.model.projection.ResumoLancamento;
import br.com.egoncalves.controlefinanceirodocker.repository.LancamentoRepository;
import br.com.egoncalves.controlefinanceirodocker.repository.filter.LancamentoFilter;
import br.com.egoncalves.controlefinanceirodocker.service.LancamentoService;
import br.com.egoncalves.controlefinanceirodocker.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author Esmael
 */
@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository lancamentoRepository;
    @Autowired
    private LancamentoService lancamentoService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private MessageSource messageSource;


    @GetMapping
    public Page<Lancamento> pesquisarLancamentos(LancamentoFilter filter, Pageable pageable) {
        return lancamentoRepository.filtrar(filter, pageable);
    }

    @GetMapping(params = "resumo")
    public Page<ResumoLancamento> resumirLancamentos(LancamentoFilter filter, Pageable pageable) {
        return lancamentoRepository.resumir(filter, pageable);
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);

        eventPublisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscarPorCodigo(@PathVariable Long codigo) {
        Lancamento lancamento = lancamentoRepository.findById(codigo).get();

        return lancamento == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(lancamento);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        lancamentoRepository.deleteById(codigo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento) {
        try {
            Lancamento lancamentoSalvo = lancamentoService.atualizar(codigo, lancamento);
            return ResponseEntity.ok(lancamentoSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<ControleFinanceiroExceptionHandler.Erro> erros = Arrays.asList(new ControleFinanceiroExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }
}

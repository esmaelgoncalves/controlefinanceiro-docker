/**
 *
 */
package br.com.egoncalves.controlefinanceirodocker.repository;

import br.com.egoncalves.controlefinanceirodocker.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Esmael
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}

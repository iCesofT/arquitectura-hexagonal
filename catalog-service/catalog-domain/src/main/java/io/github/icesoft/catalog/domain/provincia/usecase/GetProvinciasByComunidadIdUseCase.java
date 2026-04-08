package io.github.icesoft.catalog.domain.provincia.usecase;

import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import java.util.List;

/**
 * Caso de uso para obtener todas las provincias pertenecientes a una comunidad
 * autónoma. Permite consultar la estructura territorial jerárquica española.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface GetProvinciasByComunidadIdUseCase {

	/**
	 * Obtiene todas las provincias que pertenecen a una comunidad autónoma
	 * específica.
	 *
	 * @param comunidadId
	 *            Identificador único de la comunidad autónoma
	 * @return Lista de provincias pertenecientes a esa comunidad autónoma
	 * @throws IllegalArgumentException
	 *             si el comunidadId es nulo o vacío
	 */
	List<Provincia> execute(String comunidadId);
}

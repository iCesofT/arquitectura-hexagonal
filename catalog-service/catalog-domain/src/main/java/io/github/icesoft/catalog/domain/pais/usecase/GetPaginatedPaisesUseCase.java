package io.github.icesoft.catalog.domain.pais.usecase;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.pais.model.Pais;

/**
 * Caso de uso para obtener países de forma paginada. Permite consultar listas
 * de países con control de paginación para optimizar el rendimiento.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface GetPaginatedPaisesUseCase {

	/**
	 * Obtiene una página de países según los parámetros de paginación
	 * especificados.
	 * 
	 * @param paginacion
	 *            Parámetros de paginación (página y tamaño)
	 * @return Página conteniendo los países encontrados y metadatos de paginación
	 * @throws IllegalArgumentException
	 *             si los parámetros de paginación no son válidos
	 */
	Pagina<Pais> execute(Paginacion paginacion);
}
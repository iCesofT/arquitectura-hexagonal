package io.github.icesoft.catalog.domain.provincia.usecase;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;

/**
 * Caso de uso para obtener provincias de forma paginada. Permite consultar
 * listas de provincias con control de paginación para optimizar el rendimiento.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface GetPaginatedProvinciasUseCase {

	/**
	 * Obtiene una página de provincias según los parámetros de paginación
	 * especificados.
	 * 
	 * @param paginacion
	 *            Parámetros de paginación (página y tamaño)
	 * @return Página conteniendo las provincias encontradas y metadatos de
	 *         paginación
	 * @throws IllegalArgumentException
	 *             si los parámetros de paginación no son válidos
	 */
	Pagina<Provincia> execute(Paginacion paginacion);
}
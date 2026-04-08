package io.github.icesoft.catalog.domain.tipovia.usecase;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;

/**
 * Caso de uso para obtener tipos de vía de forma paginada. Permite consultar
 * listas de tipos de vía con control de paginación para optimizar el
 * rendimiento.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface GetPaginatedTiposViaUseCase {

	/**
	 * Obtiene una página de tipos de vía según los parámetros de paginación
	 * especificados.
	 * 
	 * @param paginacion
	 *            Parámetros de paginación (página y tamaño)
	 * @return Página conteniendo los tipos de vía encontrados y metadatos de
	 *         paginación
	 * @throws IllegalArgumentException
	 *             si los parámetros de paginación no son válidos
	 */
	Pagina<TipoVia> execute(Paginacion paginacion);
}
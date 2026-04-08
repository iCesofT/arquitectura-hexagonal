package io.github.icesoft.catalog.domain.comunidadautonoma.usecase;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;

/**
 * Caso de uso para obtener comunidades autónomas de forma paginada. Permite
 * consultar listas de comunidades autónomas con control de paginación para
 * optimizar el rendimiento.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface GetPaginatedComunidadesAutonomasUseCase {

	/**
	 * Obtiene una página de comunidades autónomas según los parámetros de
	 * paginación especificados.
	 * 
	 * @param paginacion
	 *            Parámetros de paginación (página y tamaño)
	 * @return Página conteniendo las comunidades autónomas encontradas y metadatos
	 *         de paginación
	 * @throws IllegalArgumentException
	 *             si los parámetros de paginación no son válidos
	 */
	Pagina<ComunidadAutonoma> execute(Paginacion paginacion);
}
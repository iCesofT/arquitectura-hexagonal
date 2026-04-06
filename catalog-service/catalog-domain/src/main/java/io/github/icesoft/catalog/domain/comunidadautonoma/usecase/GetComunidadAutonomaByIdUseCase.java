package io.github.icesoft.catalog.domain.comunidadautonoma.usecase;

import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;

/**
 * Caso de uso para obtener una comunidad autónoma por su identificador.
 * Implementa la lógica de negocio para la consulta individual de comunidades autónomas.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface GetComunidadAutonomaByIdUseCase {

	/**
	 * Obtiene una comunidad autónoma por su identificador único.
	 * 
	 * @param id Identificador único de la comunidad autónoma
	 * @return La comunidad autónoma encontrada
	 * @throws io.github.icesoft.catalog.domain.common.exception.NotFoundException si la comunidad autónoma no existe
	 * @throws IllegalArgumentException si el id es nulo o vacío
	 */
	ComunidadAutonoma execute(String id);
}
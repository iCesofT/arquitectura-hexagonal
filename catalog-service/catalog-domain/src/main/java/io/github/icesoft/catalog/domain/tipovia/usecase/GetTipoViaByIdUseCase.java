package io.github.icesoft.catalog.domain.tipovia.usecase;

import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;

/**
 * Caso de uso para obtener un tipo de vía por su identificador.
 * Implementa la lógica de negocio para la consulta individual de tipos de vía.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface GetTipoViaByIdUseCase {

	/**
	 * Obtiene un tipo de vía por su identificador único.
	 * 
	 * @param id Identificador único del tipo de vía
	 * @return El tipo de vía encontrado
	 * @throws io.github.icesoft.catalog.domain.common.exception.NotFoundException si el tipo de vía no existe
	 * @throws IllegalArgumentException si el id es nulo o vacío
	 */
	TipoVia execute(String id);
}
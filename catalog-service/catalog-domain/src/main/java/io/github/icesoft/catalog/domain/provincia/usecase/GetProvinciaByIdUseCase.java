package io.github.icesoft.catalog.domain.provincia.usecase;

import io.github.icesoft.catalog.domain.provincia.model.Provincia;

/**
 * Caso de uso para obtener una provincia por su identificador. Implementa la
 * lógica de negocio para la consulta individual de provincias.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface GetProvinciaByIdUseCase {

	/**
	 * Obtiene una provincia por su identificador único.
	 * 
	 * @param id
	 *            Identificador único de la provincia
	 * @return La provincia encontrada
	 * @throws io.github.icesoft.catalog.domain.common.exception.NotFoundException
	 *             si la provincia no existe
	 * @throws IllegalArgumentException
	 *             si el id es nulo o vacío
	 */
	Provincia execute(String id);
}
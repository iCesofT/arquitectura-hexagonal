package io.github.icesoft.catalog.domain.pais.usecase;

import io.github.icesoft.catalog.domain.pais.model.Pais;

/**
 * Caso de uso para obtener un país por su identificador. Implementa la lógica
 * de negocio para la consulta individual de países.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface GetPaisByIdUseCase {

	/**
	 * Obtiene un país por su identificador único.
	 * 
	 * @param id
	 *            Identificador único del país
	 * @return El país encontrado
	 * @throws io.github.icesoft.catalog.domain.common.exception.NotFoundException
	 *             si el país no existe
	 * @throws IllegalArgumentException
	 *             si el id es nulo o vacío
	 */
	Pais execute(String id);
}
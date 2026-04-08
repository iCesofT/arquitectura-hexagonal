package io.github.icesoft.catalog.domain.tipovia.port.repository;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import java.util.Optional;

/**
 * Puerto de repositorio para la entidad Tipo de Vía. Define las operaciones de
 * persistencia necesarias para los casos de uso de tipos de vía. Administra el
 * catálogo de tipos de vías públicas según la normativa española.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface TipoViaRepositoryPort {

	/**
	 * Obtiene todos los tipos de vía de forma paginada.
	 * 
	 * @param paginacion
	 *            Parámetros de paginación (página y tamaño)
	 * @return Página con los tipos de vía encontrados y metadatos de paginación
	 * @throws IllegalArgumentException
	 *             si los parámetros de paginación no son válidos
	 */
	Pagina<TipoVia> getTiposViaPaginados(Paginacion paginacion);

	/**
	 * Obtiene un tipo de vía por su identificador único.
	 * 
	 * @param id
	 *            Identificador único del tipo de vía
	 * @return Optional con el tipo de vía si existe, Optional.empty() si no existe
	 * @throws IllegalArgumentException
	 *             si el id es nulo o vacío
	 */
	Optional<TipoVia> getTipoViaPorId(String id);
}
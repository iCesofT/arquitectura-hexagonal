package io.github.icesoft.catalog.domain.pais.port.repository;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.pais.model.Pais;
import java.util.Optional;

/**
 * Puerto de repositorio para la entidad País.
 * Define las operaciones de persistencia necesarias para los casos de uso de países.
 * Implementa el patrón Repository con inversión de dependencias.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface PaisRepositoryPort {

	/**
	 * Obtiene todos los países de forma paginada.
	 * 
	 * @param paginacion Parámetros de paginación (página y tamaño)
	 * @return Página con los países encontrados y metadatos de paginación
	 * @throws IllegalArgumentException si los parámetros de paginación no son válidos
	 */
	Pagina<Pais> getPaisesPaginados(Paginacion paginacion);

	/**
	 * Obtiene un país por su identificador único.
	 * 
	 * @param id Identificador único del país
	 * @return Optional con el país si existe, Optional.empty() si no existe
	 * @throws IllegalArgumentException si el id es nulo o vacío
	 */
	Optional<Pais> getPaisPorId(String id);
}
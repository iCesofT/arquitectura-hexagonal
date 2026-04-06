package io.github.icesoft.catalog.domain.comunidadautonoma.port.repository;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import java.util.Optional;

/**
 * Puerto de repositorio para la entidad Comunidad Autónoma.
 * Define las operaciones de persistencia necesarias para los casos de uso de comunidades autónomas.
 * Implementa el patrón Repository con inversión de dependencias.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface ComunidadAutonomaRepositoryPort {

	/**
	 * Obtiene todas las comunidades autónomas de forma paginada.
	 * 
	 * @param paginacion Parámetros de paginación (página y tamaño)
	 * @return Página con las comunidades encontradas y metadatos de paginación
	 * @throws IllegalArgumentException si los parámetros de paginación no son válidos
	 */
	Pagina<ComunidadAutonoma> getComunidadesPaginadas(Paginacion paginacion);

	/**
	 * Obtiene una comunidad autónoma por su identificador único.
	 * 
	 * @param id Identificador único de la comunidad autónoma
	 * @return Optional con la comunidad si existe, Optional.empty() si no existe
	 * @throws IllegalArgumentException si el id es nulo o vacío
	 */
	Optional<ComunidadAutonoma> getComunidadPorId(String id);
}
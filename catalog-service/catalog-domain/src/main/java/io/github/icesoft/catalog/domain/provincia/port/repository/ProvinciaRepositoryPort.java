package io.github.icesoft.catalog.domain.provincia.port.repository;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de repositorio para la entidad Provincia. Define las operaciones de
 * persistencia necesarias para los casos de uso de provincias. Soporta
 * consultas jerárquicas por comunidad autónoma.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface ProvinciaRepositoryPort {

	/**
	 * Obtiene todas las provincias de forma paginada.
	 * 
	 * @param paginacion
	 *            Parámetros de paginación (página y tamaño)
	 * @return Página con las provincias encontradas y metadatos de paginación
	 * @throws IllegalArgumentException
	 *             si los parámetros de paginación no son válidos
	 */
	Pagina<Provincia> getProvinciasPaginadas(Paginacion paginacion);

	/**
	 * Obtiene una provincia por su identificador único.
	 *
	 * @param id
	 *            Identificador único de la provincia
	 * @return Optional con la provincia si existe, Optional.empty() si no existe
	 * @throws IllegalArgumentException
	 *             si el id es nulo o vacío
	 */
	Optional<Provincia> getProvinciaPorId(String id);

	/**
	 * Obtiene todas las provincias pertenecientes a una comunidad autónoma.
	 *
	 * @param comunidadId
	 *            Identificador único de la comunidad autónoma
	 * @return Lista de provincias en esa comunidad autónoma
	 * @throws IllegalArgumentException
	 *             si el comunidadId es nulo o vacío
	 */
	List<Provincia> getProvinciasByComunidadId(String comunidadId);
}
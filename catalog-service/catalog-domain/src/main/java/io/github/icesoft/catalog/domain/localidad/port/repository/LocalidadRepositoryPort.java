package io.github.icesoft.catalog.domain.localidad.port.repository;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.localidad.model.Localidad;
import java.util.Optional;

/**
 * Puerto de repositorio para la entidad Localidad.
 * Define las operaciones de persistencia necesarias para los casos de uso de localidades.
 * Soporta búsquedas con filtros de texto para facilitar la localización de poblaciones.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface LocalidadRepositoryPort {

	/**
	 * Obtiene todas las localidades de forma paginada con capacidad de filtrado por texto.
	 * 
	 * @param paginacion Parámetros de paginación (página y tamaño)
	 * @param query Texto de búsqueda para filtrar localidades por nombre (puede ser null)
	 * @return Página con las localidades encontradas y metadatos de paginación
	 * @throws IllegalArgumentException si los parámetros de paginación no son válidos
	 */
	Pagina<Localidad> getLocalidadesPaginadas(Paginacion paginacion, String query);

	/**
	 * Obtiene una localidad por su identificador único.
	 * 
	 * @param id Identificador único de la localidad
	 * @return Optional con la localidad si existe, Optional.empty() si no existe
	 * @throws IllegalArgumentException si el id es nulo o vacío
	 */
	Optional<Localidad> getLocalidadPorId(String id);
}
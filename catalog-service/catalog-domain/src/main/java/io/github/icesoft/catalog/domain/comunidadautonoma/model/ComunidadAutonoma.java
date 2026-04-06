package io.github.icesoft.catalog.domain.comunidadautonoma.model;

import java.util.Objects;

/**
 * Entidad de dominio que representa una Comunidad Autónoma española.
 * Incluye códigos oficiales para integración con sistemas gubernamentales.
 * 
 * @param id Identificador único de la comunidad autónoma
 * @param nombre Nombre oficial de la comunidad autónoma
 * @param rcp Código RCP (Registro Central de Personal) de la comunidad
 * @param dir2 Código DIR2 (Directorio Común de unidades orgánicas y oficinas)
 * 
 * @throws NullPointerException si id o nombre son nulos
 * @throws IllegalArgumentException si id o nombre están vacíos
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public record ComunidadAutonoma(String id, String nombre, String rcp, String dir2) {

	public ComunidadAutonoma {
		Objects.requireNonNull(id, "El ID de la comunidad autónoma no puede ser nulo");
		Objects.requireNonNull(nombre, "El nombre de la comunidad autónoma no puede ser nulo");

		if (id.trim().isEmpty()) {
			throw new IllegalArgumentException("El ID de la comunidad autónoma no puede estar vacío");
		}
		if (nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la comunidad autónoma no puede estar vacío");
		}
	}

}
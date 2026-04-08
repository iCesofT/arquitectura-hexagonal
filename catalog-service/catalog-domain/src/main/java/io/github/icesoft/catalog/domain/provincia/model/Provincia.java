package io.github.icesoft.catalog.domain.provincia.model;

import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import java.util.Objects;

/**
 * Entidad de dominio que representa una Provincia española. Cada provincia
 * pertenece a una comunidad autónoma específica.
 * 
 * @param id
 *            Identificador único de la provincia
 * @param nombre
 *            Nombre oficial de la provincia
 * @param comunidadAutonoma
 *            Comunidad autónoma a la que pertenece esta provincia
 * 
 * @throws NullPointerException
 *             si algún parámetro requerido es nulo
 * @throws IllegalArgumentException
 *             si id o nombre están vacíos
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public record Provincia(

		String id, String nombre, ComunidadAutonoma comunidadAutonoma) {

	public Provincia(String id, String nombre, ComunidadAutonoma comunidadAutonoma) {
		this.id = Objects.requireNonNull(id, "El ID de la provincia no puede ser nulo");
		this.nombre = Objects.requireNonNull(nombre, "El nombre de la provincia no puede ser nulo");
		this.comunidadAutonoma = Objects.requireNonNull(comunidadAutonoma, "La comunidad autónoma no puede ser nula");

		if (id.trim().isEmpty()) {
			throw new IllegalArgumentException("El ID de la provincia no puede estar vacío");
		}
		if (nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la provincia no puede estar vacío");
		}
	}

}
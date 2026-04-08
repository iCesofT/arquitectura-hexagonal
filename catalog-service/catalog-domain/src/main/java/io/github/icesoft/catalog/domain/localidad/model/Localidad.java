package io.github.icesoft.catalog.domain.localidad.model;

import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import java.util.Objects;

/**
 * Entidad de dominio que representa una Localidad española. Cada localidad
 * pertenece a una provincia específica.
 * 
 * @param id
 *            Identificador único de la localidad
 * @param nombre
 *            Nombre oficial de la localidad
 * @param provincia
 *            Provincia a la que pertenece esta localidad
 * 
 * @throws NullPointerException
 *             si algún parámetro requerido es nulo
 * @throws IllegalArgumentException
 *             si id o nombre están vacíos
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public record Localidad(

		String id, String nombre, Provincia provincia) {

	public Localidad(String id, String nombre, Provincia provincia) {
		this.id = Objects.requireNonNull(id, "El ID de la localidad no puede ser nulo");
		this.nombre = Objects.requireNonNull(nombre, "El nombre de la localidad no puede ser nulo");
		this.provincia = Objects.requireNonNull(provincia, "La provincia no puede ser nula");

		if (id.trim().isEmpty()) {
			throw new IllegalArgumentException("El ID de la localidad no puede estar vacío");
		}
		if (nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la localidad no puede estar vacío");
		}
	}

}
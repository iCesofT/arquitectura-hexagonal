package io.github.icesoft.catalog.domain.pais.model;

import java.util.Objects;

/**
 * Entidad de dominio que representa un País según los estándares internacionales.
 * Incorpora códigos ISO 3166-1 alfa-2 y alfa-3 para identificación estándar.
 * 
 * @param id Identificador único del país
 * @param nombre Nombre oficial del país
 * @param alfa2 Código ISO 3166-1 alfa-2 de dos letras (ej: ES, FR, IT)
 * @param alfa3 Código ISO 3166-1 alfa-3 de tres letras (ej: ESP, FRA, ITA)
 * 
 * @throws NullPointerException si algún parámetro requerido es nulo
 * @throws IllegalArgumentException si id o nombre están vacíos
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public record Pais(String id, String nombre, String alfa2, String alfa3) {

	public Pais {
		Objects.requireNonNull(id, "El ID del país no puede ser nulo");
		Objects.requireNonNull(nombre, "El nombre del país no puede ser nulo");

		if (id.trim().isEmpty()) {
			throw new IllegalArgumentException("El ID del país no puede estar vacío");
		}
		if (nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del país no puede estar vacío");
		}
	}

}
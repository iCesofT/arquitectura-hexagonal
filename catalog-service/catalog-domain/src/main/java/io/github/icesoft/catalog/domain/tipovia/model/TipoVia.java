package io.github.icesoft.catalog.domain.tipovia.model;

import java.util.Objects;

/**
 * Entidad de dominio que representa un Tipo de Vía según la normativa española.
 * Define los diferentes tipos de vías públicas y sus abreviaturas oficiales.
 * 
 * @param id Identificador único del tipo de vía
 * @param nombre Nombre completo del tipo de vía (ej: Calle, Avenida, Plaza)
 * @param abreviatura Abreviatura oficial del tipo de vía (ej: C/, Avda., Pl.)
 * 
 * @throws NullPointerException si algún parámetro requerido es nulo
 * @throws IllegalArgumentException si algún campo está vacío
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public record TipoVia(

		String id, String nombre, String abreviatura) {

	public TipoVia(String id, String nombre, String abreviatura) {
		this.id = Objects.requireNonNull(id, "El ID del tipo de vía no puede ser nulo");
		this.nombre = Objects.requireNonNull(nombre, "El nombre del tipo de vía no puede ser nulo");
		this.abreviatura = Objects.requireNonNull(abreviatura, "La abreviatura del tipo de vía no puede ser nula");

		if (id.trim().isEmpty()) {
			throw new IllegalArgumentException("El ID del tipo de vía no puede estar vacío");
		}
		if (nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del tipo de vía no puede estar vacío");
		}
		if (abreviatura.trim().isEmpty()) {
			throw new IllegalArgumentException("La abreviatura del tipo de vía no puede estar vacía");
		}
	}
}
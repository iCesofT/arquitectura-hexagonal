package io.github.icesoft.catalog.domain.common.model;

/**
 * Representa los parámetros de paginación para solicitudes de consulta.
 * Define qué página solicitar y cuántos elementos incluir por página.
 * 
 * @param page Número de página a solicitar (basado en 0)
 * @param size Cantidad de elementos por página
 * 
 * @throws IllegalArgumentException si page es negativo o size es menor o igual a 0
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public record Paginacion(int page, int size) {

	public Paginacion {
		if (page < 0) {
			throw new IllegalArgumentException("El número de página no puede ser negativo");
		}
		if (size <= 0) {
			throw new IllegalArgumentException("El tamaño de página debe ser mayor a cero");
		}
	}

	public static Paginacion of(int page, int size) {
		return new Paginacion(page, size);
	}

	public int getOffset() {
		return page * size;
	}

	// Métodos de compatibilidad para facilitar la migración
	public int getNumeroPagina() {
		return page;
	}

	public int getTamanoPagina() {
		return size;
	}
}
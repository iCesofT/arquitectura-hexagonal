package io.github.icesoft.catalog.domain.common.model;

import java.util.List;
import java.util.Objects;

/**
 * Representa una página de resultados en el dominio para implementar paginación.
 * Encapsula los elementos de la página actual junto con metadatos de paginación.
 * 
 * @param <T> Tipo de elementos contenidos en la página
 * @param items Lista de elementos en la página actual
 * @param page Número de página actual (basado en 0)
 * @param size Tamaño de la página (cantidad de elementos por página)
 * @param totalItems Total de elementos disponibles en todas las páginas
 * 
 * @throws NullPointerException si items es nulo
 * @throws IllegalArgumentException si page es negativo, size es menor o igual a 0, o totalItems es negativo
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public record Pagina<T>(List<T> items, int page, int size, int totalItems) {

	public Pagina {
		items = Objects.requireNonNull(items, "Los items no pueden ser nulos");

		if (page < 0) {
			throw new IllegalArgumentException("El número de página no puede ser negativo");
		}
		if (size <= 0) {
			throw new IllegalArgumentException("El tamaño de página debe ser mayor a cero");
		}
		if (totalItems < 0) {
			throw new IllegalArgumentException("El total de items no puede ser negativo");
		}
	}

	public int getTotalPaginas() {
		if (size <= 0) {
			return 0;
		}
		return (int) Math.ceil((double) totalItems / size);
	}

	public boolean esUltimaPagina() {
		return page >= getTotalPaginas() - 1;
	}

	public boolean esPrimeraPagina() {
		return page == 0;
	}

	// Factory method para facilitar la creación
	public static <T> Pagina<T> of(List<T> items, int page, int size, long totalItems) {
		return new Pagina<>(items, page, size, (int) totalItems);
	}

	// Métodos de compatibilidad para facilitar la migración
	public List<T> getItems() {
		return items;
	}

	public int getNumeroPagina() {
		return page;
	}

	public int getTamanoPagina() {
		return size;
	}

	public int getTotalItems() {
		return totalItems;
	}
}
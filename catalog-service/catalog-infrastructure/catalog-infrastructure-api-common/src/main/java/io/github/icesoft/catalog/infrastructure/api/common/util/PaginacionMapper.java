package io.github.icesoft.catalog.infrastructure.api.common.util;

import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.infrastructure.api.common.config.PaginationConfig;
import org.springframework.stereotype.Component;

/**
 * Utilidad para mapeo y validación de parámetros de paginación.
 * Aplica la configuración centralizada de paginación y valida límites
 * para prevenir solicitudes abusivas.
 * 
 * <p>Centraliza la lógica de construcción de objetos Paginacion aplicando
 * valores por defecto y límites máximos configurados.</p>
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Component
public class PaginacionMapper {

	private final PaginationConfig paginationConfig;

	/**
	 * Constructor que inyecta la configuración de paginación.
	 * 
	 * @param paginationConfig Configuración de parámetros de paginación
	 */
	public PaginacionMapper(PaginationConfig paginationConfig) {
		this.paginationConfig = paginationConfig;
	}

	public Paginacion toPaginacion(Integer page, Integer size) {
		int numeroPagina = page != null ? page : 0;
		int tamanoPagina = size != null ? size : paginationConfig.getDefaultPageSize();

		return of(numeroPagina, tamanoPagina);
	}

	public Paginacion toPaginacionWithDefaults() {
		return of(0, paginationConfig.getDefaultPageSize());
	}

	private Paginacion of(int page, int size) {
		if (page < 0) {
			throw new IllegalArgumentException("El número de página no puede ser negativo");
		}
		if (size <= 0) {
			throw new IllegalArgumentException("El tamaño de página debe ser mayor a cero");
		}
		if (size > paginationConfig.getMaxPageSize()) {
			throw new IllegalArgumentException(String.format(
					"El tamaño de página no puede exceder el máximo permitido: %d", paginationConfig.getMaxPageSize()));
		}
		return new Paginacion(page, size);
	}

}

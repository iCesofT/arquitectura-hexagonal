package io.github.icesoft.catalog.infrastructure.api.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuración para las propiedades de paginación en el sistema.
 * Lee los valores de spring.data.web.pageable del application.yaml y los
 * expone para uso en todas las capas de la aplicación (REST, gRPC, etc.).
 * 
 * <p>Permite centralizar la configuración de paginación y mantener consistencia
 * entre diferentes interfaces de la API.</p>
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Component
@ConfigurationProperties("spring.data.web.pageable")
public class PaginationConfig {

	/**
	 * Tamaño de página por defecto cuando no se especifica en la solicitud.
	 */
	@Getter
	@Setter
	private int defaultPageSize = 20;
	
	/**
	 * Tamaño máximo de página permitido para prevenir sobrecarga del sistema.
	 */
	@Getter
	@Setter
	private int maxPageSize = 100;

}
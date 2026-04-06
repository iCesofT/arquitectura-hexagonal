package io.github.icesoft.catalog.domain.common.event;

import java.time.Instant;

/**
 * Interfaz base para todos los eventos de dominio.
 * Los eventos representan hechos importantes que han ocurrido en el dominio
 * y que pueden ser de interés para otros bounded contexts.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface DomainEvent {
	
	/**
	 * Obtiene el momento en que ocurrió el evento.
	 * 
	 * @return Timestamp de cuando ocurrió el evento
	 */
	Instant ocurredAt();
}

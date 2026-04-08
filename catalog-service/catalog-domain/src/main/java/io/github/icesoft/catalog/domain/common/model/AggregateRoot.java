package io.github.icesoft.catalog.domain.common.model;

import io.github.icesoft.catalog.domain.common.event.DomainEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase base abstracta para agregados de dominio que necesitan publicar
 * eventos. Implementa el patrón Domain Events para desacoplamiento entre
 * bounded contexts. Los agregados pueden levantar eventos de dominio que serán
 * procesados posteriormente.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public abstract class AggregateRoot {

	private final List<DomainEvent> domainEvents = new ArrayList<>();

	/**
	 * Levanta un evento de dominio que será procesado posteriormente. El evento se
	 * agrega a la lista interna de eventos pendientes.
	 * 
	 * @param event
	 *            Evento de dominio a levantar
	 */
	protected void raise(DomainEvent event) {
		domainEvents.add(event);
	}

	/**
	 * Extrae y limpia todos los eventos de dominio pendientes en una operación
	 * atómica. Los eventos son removidos de la lista interna después de ser
	 * extraídos.
	 * 
	 * @return Lista inmutable con todos los eventos pendientes
	 */
	public List<DomainEvent> pullDomainEvents() {
		var copy = List.copyOf(domainEvents);
		domainEvents.clear();
		return copy;
	}

	/**
	 * Consulta los eventos de dominio pendientes sin removerlos. Útil para
	 * inspección sin afectar el estado interno.
	 * 
	 * @return Vista inmutable de los eventos pendientes
	 */
	public List<DomainEvent> peekDomainEvents() {
		return Collections.unmodifiableList(domainEvents);
	}
}

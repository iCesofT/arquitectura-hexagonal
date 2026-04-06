package io.github.icesoft.catalog.application.mediator.behaviors;

import io.github.icesoft.catalog.application.event.DomainEventCollector;
import io.github.icesoft.catalog.application.mediator.PipelineBehavior;
import io.github.icesoft.catalog.application.mediator.Request;
import io.github.icesoft.catalog.domain.common.event.DomainEvent;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Order(40)
public class DomainEventsBehavior implements PipelineBehavior {

	private final DomainEventCollector collector;
	private final ApplicationEventPublisher publisher;

	public DomainEventsBehavior(DomainEventCollector collector, ApplicationEventPublisher publisher) {
		this.collector = collector;
		this.publisher = publisher;
	}

	@Override
	public <R> R handle(Request<R> request, Supplier<R> next) {
		try {
			R result = next.get();

			List<DomainEvent> events = collector.drain();
			if (events.isEmpty())
				return result;

			if (TransactionSynchronizationManager.isActualTransactionActive()) {
				TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
					@Override
					public void afterCommit() {
						events.forEach(publisher::publishEvent);
					}
				});
			} else {
				// si no hay tx, publica inmediatamente
				events.forEach(publisher::publishEvent);
			}

			return result;
		} finally {
			collector.clear();
		}
	}
}

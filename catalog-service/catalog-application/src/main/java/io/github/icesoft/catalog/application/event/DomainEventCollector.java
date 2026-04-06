package io.github.icesoft.catalog.application.event;

import io.github.icesoft.catalog.domain.common.event.DomainEvent;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DomainEventCollector {

	private static final ThreadLocal<List<DomainEvent>> TL = ThreadLocal.withInitial(ArrayList::new);

	public void addAll(List<DomainEvent> events) {
		TL.get().addAll(events);
	}

	public List<DomainEvent> drain() {
		var events = List.copyOf(TL.get());
		TL.remove();
		return events;
	}

	public void clear() {
		TL.remove();
	}
}

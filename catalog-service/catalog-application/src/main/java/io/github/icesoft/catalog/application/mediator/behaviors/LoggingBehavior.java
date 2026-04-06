package io.github.icesoft.catalog.application.mediator.behaviors;

import io.github.icesoft.catalog.application.mediator.PipelineBehavior;
import io.github.icesoft.catalog.application.mediator.Request;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10)
@Slf4j
public class LoggingBehavior implements PipelineBehavior {

	@Override
	public <R> R handle(Request<R> request, Supplier<R> next) {
		var name = request.getClass().getSimpleName();
		long t0 = System.nanoTime();
		try {
			log.info("Handling {}", name);
			var result = next.get();
			log.info("Handled {} in {} ms", name, (System.nanoTime() - t0) / 1_000_000);
			return result;
		} catch (RuntimeException e) {
			log.error("Failed {}: {}", name, e.getMessage(), e);
			throw e;
		}
	}
}

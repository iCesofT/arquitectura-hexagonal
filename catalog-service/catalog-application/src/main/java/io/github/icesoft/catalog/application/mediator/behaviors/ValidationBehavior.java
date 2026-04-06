package io.github.icesoft.catalog.application.mediator.behaviors;

import io.github.icesoft.catalog.application.mediator.PipelineBehavior;
import io.github.icesoft.catalog.application.mediator.Request;
import io.github.icesoft.catalog.domain.common.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(20)
public class ValidationBehavior implements PipelineBehavior {

	private final Validator validator;

	@Override
	public <R> R handle(Request<R> request, Supplier<R> next) {
		Set<ConstraintViolation<Request<R>>> violations = validator.validate(request);

		if (!violations.isEmpty()) {
			var msg = violations.stream().map(v -> v.getPropertyPath() + ": " + v.getMessage())
					.collect(Collectors.joining(", "));
			throw new ValidationException(msg);
		}

		return next.get();
	}
}

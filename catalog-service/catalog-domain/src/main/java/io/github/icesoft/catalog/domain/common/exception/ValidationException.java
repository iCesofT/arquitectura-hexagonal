package io.github.icesoft.catalog.domain.common.exception;

public class ValidationException extends DomainException {

	public ValidationException(String message) {
		super(new ErrorType() {
			@Override
			public int getStatus() {
				return 404;
			}

			@Override
			public String getTitle() {
				return "No encontrado";
			}

			@Override
			public String getDetail() {
				return "El recurso solicitado no existe: '%s'";
			}
		}, new String[]{message});
	}
}

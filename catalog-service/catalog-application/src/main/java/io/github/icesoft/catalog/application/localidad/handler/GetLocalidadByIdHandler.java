package io.github.icesoft.catalog.application.localidad.handler;

import io.github.icesoft.catalog.application.localidad.query.GetLocalidadByIdQuery;
import io.github.icesoft.catalog.application.mediator.RequestHandler;
import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.localidad.model.Localidad;
import io.github.icesoft.catalog.domain.localidad.port.repository.LocalidadRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetLocalidadByIdHandler implements RequestHandler<GetLocalidadByIdQuery, Localidad> {

	private final LocalidadRepositoryPort repository;

	@Override
	public Localidad handle(GetLocalidadByIdQuery request) {
		return repository.getLocalidadPorId(request.id())
				.orElseThrow(() -> new NotFoundException("Localidad no encontrada", request.id()));
	}
}

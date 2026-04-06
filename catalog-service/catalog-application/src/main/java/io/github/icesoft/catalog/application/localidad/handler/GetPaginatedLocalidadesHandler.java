package io.github.icesoft.catalog.application.localidad.handler;

import io.github.icesoft.catalog.application.localidad.query.GetPaginatedLocalidadesQuery;
import io.github.icesoft.catalog.application.mediator.RequestHandler;
import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.localidad.model.Localidad;
import io.github.icesoft.catalog.domain.localidad.port.repository.LocalidadRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPaginatedLocalidadesHandler implements RequestHandler<GetPaginatedLocalidadesQuery, Pagina<Localidad>> {

	private final LocalidadRepositoryPort repository;

	@Override
	public Pagina<Localidad> handle(GetPaginatedLocalidadesQuery request) {
		return repository.getLocalidadesPaginadas(request.paginacion(), request.query());
	}
}

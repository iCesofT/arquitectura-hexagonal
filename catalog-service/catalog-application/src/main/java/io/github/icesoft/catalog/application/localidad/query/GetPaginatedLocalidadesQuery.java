package io.github.icesoft.catalog.application.localidad.query;

import io.github.icesoft.catalog.application.mediator.Request;
import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.localidad.model.Localidad;

public record GetPaginatedLocalidadesQuery(Paginacion paginacion, String query) implements Request<Pagina<Localidad>> {
}

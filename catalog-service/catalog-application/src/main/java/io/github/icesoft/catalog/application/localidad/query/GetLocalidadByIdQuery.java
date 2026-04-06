package io.github.icesoft.catalog.application.localidad.query;

import io.github.icesoft.catalog.application.mediator.Request;
import io.github.icesoft.catalog.domain.localidad.model.Localidad;
import jakarta.validation.constraints.NotNull;

public record GetLocalidadByIdQuery(@NotNull String id) implements Request<Localidad> {
}

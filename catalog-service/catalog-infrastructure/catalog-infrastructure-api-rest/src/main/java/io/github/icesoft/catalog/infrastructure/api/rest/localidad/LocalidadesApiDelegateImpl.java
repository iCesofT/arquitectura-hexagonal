package io.github.icesoft.catalog.infrastructure.api.rest.localidad;

import io.github.icesoft.catalog.apimodel.api.LocalidadesApiDelegate;
import io.github.icesoft.catalog.apimodel.model.LocalidadDetalleDto;
import io.github.icesoft.catalog.apimodel.model.LocalidadPageResultDto;
import io.github.icesoft.catalog.apimodel.model.LocalidadResumenDto;
import io.github.icesoft.catalog.application.localidad.query.GetLocalidadByIdQuery;
import io.github.icesoft.catalog.application.localidad.query.GetPaginatedLocalidadesQuery;
import io.github.icesoft.catalog.application.mediator.Mediator;
import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.localidad.model.Localidad;
import io.github.icesoft.catalog.infrastructure.api.common.util.PaginacionMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Implementation of LocalidadesApiDelegate
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class LocalidadesApiDelegateImpl implements LocalidadesApiDelegate {

	private final LocalidadMapper localidadMapper;
	private final Mediator mediator;
	private final PaginacionMapper paginacionMapper;

	@Override
	public ResponseEntity<LocalidadDetalleDto> getLocalidadById(String id, String lang) {
		log.info("Getting localidad detail for id: {} and language: {}", id, lang);
		Localidad localidad = mediator.send(new GetLocalidadByIdQuery(id));
		return ResponseEntity.ok(localidadMapper.toDetalleDto(localidad));
	}

	@Override
	public ResponseEntity<LocalidadPageResultDto> getLocalidades(String lang, Integer page, Integer size,
			String query) {
		log.info("Getting localidades list for language: {}, page: {}, size: {}", lang, page, size);

		Paginacion paginacion = paginacionMapper.toPaginacion(page, size);
		Pagina<Localidad> paginaLocalidades = mediator.send(new GetPaginatedLocalidadesQuery(paginacion, query));

		// Mapear las entidades de dominio a DTOs
		List<LocalidadResumenDto> localidadesDto = paginaLocalidades.getItems().stream()
				.map(localidadMapper::toResumenDto).toList();

		// Crear el resultado de página tipado para Localidades
		LocalidadPageResultDto result = new LocalidadPageResultDto().items(localidadesDto)
				.page(paginaLocalidades.getNumeroPagina()).size(paginaLocalidades.size())
				.totalItems(paginaLocalidades.getTotalItems());

		return ResponseEntity.ok(result);
	}
}
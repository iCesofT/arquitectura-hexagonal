package io.github.icesoft.catalog.infrastructure.api.rest.tipovia;

import io.github.icesoft.catalog.apimodel.api.TiposViaApiDelegate;
import io.github.icesoft.catalog.apimodel.model.TipoViaDetalleDto;
import io.github.icesoft.catalog.apimodel.model.TipoViaResumenDto;
import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import io.github.icesoft.catalog.domain.tipovia.usecase.GetPaginatedTiposViaUseCase;
import io.github.icesoft.catalog.domain.tipovia.usecase.GetTipoViaByIdUseCase;
import io.github.icesoft.catalog.infrastructure.api.common.util.PaginacionMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation of TiposViaApiDelegate
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TiposViaApiDelegateImpl implements TiposViaApiDelegate {

	private final GetPaginatedTiposViaUseCase getPaginatedTiposViaUseCase;
	private final GetTipoViaByIdUseCase getTipoViaByIdUseCase;
	private final TipoViaMapper tipoViaMapper;
	private final PaginacionMapper paginacionMapper;

	@Override
	public ResponseEntity<TipoViaDetalleDto> getTipoViaById(String id, String lang) {
		log.info("Getting tipo via detail for id: {} and language: {}", id, lang);

		TipoVia detalle = getTipoViaByIdUseCase.execute(id);
		return ResponseEntity.ok(tipoViaMapper.toDetalleDto(detalle));
	}

	@Override
	public ResponseEntity<List<TipoViaResumenDto>> getTiposVia(String lang) {
		log.info("Getting tipos via list for language: {}", lang);

		// Usar paginación config con tamaño personalizado para listados completos
		Paginacion paginacion = paginacionMapper.toPaginacion(0, 50);
		Pagina<TipoVia> paginaTiposVia = getPaginatedTiposViaUseCase.execute(paginacion);

		List<TipoViaResumenDto> tiposVia = paginaTiposVia.getItems().stream().map(tipoViaMapper::toResumenDto).toList();

		return ResponseEntity.ok(tiposVia);
	}
}
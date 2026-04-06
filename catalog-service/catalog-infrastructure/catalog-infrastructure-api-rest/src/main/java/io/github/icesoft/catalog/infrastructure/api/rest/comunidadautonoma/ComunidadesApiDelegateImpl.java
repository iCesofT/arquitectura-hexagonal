package io.github.icesoft.catalog.infrastructure.api.rest.comunidadautonoma;

import io.github.icesoft.catalog.apimodel.api.ComunidadesApiDelegate;
import io.github.icesoft.catalog.apimodel.model.ComunidadDetalleDto;
import io.github.icesoft.catalog.apimodel.model.ComunidadResumenDto;
import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.comunidadautonoma.usecase.GetComunidadAutonomaByIdUseCase;
import io.github.icesoft.catalog.domain.comunidadautonoma.usecase.GetPaginatedComunidadesAutonomasUseCase;
import io.github.icesoft.catalog.infrastructure.api.common.util.PaginacionMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation of ComunidadesApiDelegate
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ComunidadesApiDelegateImpl implements ComunidadesApiDelegate {

	private final GetPaginatedComunidadesAutonomasUseCase getPaginatedComunidadesAutonomasUseCase;
	private final GetComunidadAutonomaByIdUseCase getComunidadAutonomaByIdUseCase;
	private final ComunidadAutonomaMapper comunidadMapper;
	private final PaginacionMapper paginacionMapper;

	@Override
	public ResponseEntity<ComunidadDetalleDto> getComunidadById(String id, String lang) {
		log.info("Getting comunidad detail for id: {} and language: {}", id, lang);

		ComunidadAutonoma comunidad = getComunidadAutonomaByIdUseCase.execute(id);
		return ResponseEntity.ok(comunidadMapper.toDetalleDto(comunidad));
	}

	@Override
	public ResponseEntity<List<ComunidadResumenDto>> getComunidades(String lang) {
		log.info("Getting comunidades list for language: {}", lang);

		// Usar paginación config con tamaño personalizado para listados completos
		Paginacion paginacion = paginacionMapper.toPaginacion(0, 50);
		Pagina<ComunidadAutonoma> paginaComunidades = getPaginatedComunidadesAutonomasUseCase.execute(paginacion);

		List<ComunidadResumenDto> comunidades = paginaComunidades.getItems().stream().map(comunidadMapper::toResumenDto)
				.toList();

		return ResponseEntity.ok(comunidades);
	}
}
package io.github.icesoft.catalog.infrastructure.api.rest.provincia;

import io.github.icesoft.catalog.apimodel.api.ProvinciasApiDelegate;
import io.github.icesoft.catalog.apimodel.model.ProvinciaDetalleDto;
import io.github.icesoft.catalog.apimodel.model.ProvinciaResumenDto;
import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.domain.provincia.usecase.GetPaginatedProvinciasUseCase;
import io.github.icesoft.catalog.domain.provincia.usecase.GetProvinciaByIdUseCase;
import io.github.icesoft.catalog.infrastructure.api.common.util.PaginacionMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation of ProvinciasApiDelegate
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProvinciasApiDelegateImpl implements ProvinciasApiDelegate {

	private final GetPaginatedProvinciasUseCase getPaginatedProvinciasUseCase;
	private final GetProvinciaByIdUseCase getProvinciaByIdUseCase;
	private final ProvinciaMapper provinciaMapper;
	private final PaginacionMapper paginacionMapper;

	@Override
	public ResponseEntity<ProvinciaDetalleDto> getProvinciaById(String id, String lang) {
		log.info("Getting provincia detail for id: {} and language: {}", id, lang);

		Provincia provincia = getProvinciaByIdUseCase.execute(id);
		return ResponseEntity.ok(provinciaMapper.toDetalleDto(provincia));
	}

	@Override
	public ResponseEntity<List<ProvinciaResumenDto>> getProvincias(String lang) {
		log.info("Getting provincias list for language: {}", lang);

		// Usar paginación config con tamaño personalizado para listados completos
		Paginacion paginacion = paginacionMapper.toPaginacion(0, 50);
		Pagina<Provincia> paginaProvincias = getPaginatedProvinciasUseCase.execute(paginacion);

		List<ProvinciaResumenDto> provincias = paginaProvincias.getItems().stream().map(provinciaMapper::toResumenDto)
				.toList();

		return ResponseEntity.ok(provincias);
	}
}
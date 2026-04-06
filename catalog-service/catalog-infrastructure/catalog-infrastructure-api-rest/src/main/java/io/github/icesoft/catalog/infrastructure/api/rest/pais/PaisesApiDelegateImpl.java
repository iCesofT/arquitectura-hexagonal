package io.github.icesoft.catalog.infrastructure.api.rest.pais;

import io.github.icesoft.catalog.apimodel.api.PaisesApiDelegate;
import io.github.icesoft.catalog.apimodel.model.PaisDetalleDto;
import io.github.icesoft.catalog.apimodel.model.PaisResumenDto;
import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.pais.model.Pais;
import io.github.icesoft.catalog.domain.pais.usecase.GetPaginatedPaisesUseCase;
import io.github.icesoft.catalog.domain.pais.usecase.GetPaisByIdUseCase;
import io.github.icesoft.catalog.infrastructure.api.common.util.PaginacionMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation of PaisesApiDelegate
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaisesApiDelegateImpl implements PaisesApiDelegate {

	private final GetPaginatedPaisesUseCase getPaginatedPaisesUseCase;
	private final GetPaisByIdUseCase getPaisByIdUseCase;
	private final PaisMapper paisMapper;
	private final PaginacionMapper paginacionMapper;

	@Override
	public ResponseEntity<PaisDetalleDto> getPaisById(String id, String lang) {
		log.info("Getting pais detail for id: {} and language: {}", id, lang);

		Pais pais = getPaisByIdUseCase.execute(id);
		return ResponseEntity.ok(paisMapper.toDetalleDto(pais));
	}

	@Override
	public ResponseEntity<List<PaisResumenDto>> getPaises(String lang) {
		log.info("Getting paises list for language: {}", lang);

		// Usar paginación config con tamaño personalizado para listados completos
		Paginacion paginacion = paginacionMapper.toPaginacion(0, 50);
		Pagina<Pais> paginaPaises = getPaginatedPaisesUseCase.execute(paginacion);

		List<PaisResumenDto> paises = paginaPaises.getItems().stream().map(paisMapper::toResumenDto).toList();

		return ResponseEntity.ok(paises);
	}
}
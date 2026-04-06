package io.github.icesoft.catalog.persistence.comunidadautonoma.adapter;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.comunidadautonoma.port.repository.ComunidadAutonomaRepositoryPort;
import io.github.icesoft.catalog.persistence.comunidadautonoma.entities.ComunidadAutonomaEntity;
import io.github.icesoft.catalog.persistence.comunidadautonoma.mapper.ComunidadAutonomaEntityMapper;
import io.github.icesoft.catalog.persistence.comunidadautonoma.repository.JpaComunidadAutonomaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Implementación JPA del DAO de ComunidadAutonoma
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ComunidadAutonomaRepositoryAdapter implements ComunidadAutonomaRepositoryPort {

	private final JpaComunidadAutonomaRepository comunidadRepository;
	private final ComunidadAutonomaEntityMapper comunidadMapper;

	@Override
	public Pagina<ComunidadAutonoma> getComunidadesPaginadas(Paginacion paginacion) {
		log.debug("Getting paginated autonomous communities with pagination: {}", paginacion);

		Pageable pageable = PageRequest.of(paginacion.page(), paginacion.size(),
				Sort.by(Sort.Direction.ASC, "denominacion"));

		Page<ComunidadAutonomaEntity> pageResult = comunidadRepository.findAll(pageable);

		List<ComunidadAutonoma> comunidades = pageResult.getContent().stream().map(comunidadMapper::toDomain).toList();

		return Pagina.of(comunidades, paginacion.page(), paginacion.size(), pageResult.getTotalElements());
	}

	@Override
	public Optional<ComunidadAutonoma> getComunidadPorId(String id) {
		log.debug("Finding autonomous community by id: {}", id);

		return comunidadRepository.findById(id).map(comunidadMapper::toDomain);
	}

}
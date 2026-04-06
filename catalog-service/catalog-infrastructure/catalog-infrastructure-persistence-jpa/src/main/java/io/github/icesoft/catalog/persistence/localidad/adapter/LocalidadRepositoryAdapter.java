package io.github.icesoft.catalog.persistence.localidad.adapter;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.localidad.model.Localidad;
import io.github.icesoft.catalog.domain.localidad.port.repository.LocalidadRepositoryPort;
import io.github.icesoft.catalog.persistence.localidad.entities.LocalidadEntity;
import io.github.icesoft.catalog.persistence.localidad.mapper.LocalidadEntityMapper;
import io.github.icesoft.catalog.persistence.localidad.repository.JpaLocalidadRepository;
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
 * Implementación JPA del DAO de Localidad
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LocalidadRepositoryAdapter implements LocalidadRepositoryPort {

	private final JpaLocalidadRepository localidadRepository;
	private final LocalidadEntityMapper localidadMapper;

	@Override
	// @Cacheable(value = "localidades", key = "#paginacion.page() + '-' +
	// #paginacion.size() + '-' + (#query != null ? #query : '')")
	public Pagina<Localidad> getLocalidadesPaginadas(Paginacion paginacion, String query) {
		log.debug("Getting paginated localities with pagination: {}", paginacion);

		Pageable pageable = PageRequest.of(paginacion.page(), paginacion.size(),
				Sort.by(Sort.Direction.ASC, "denominacion"));

		Page<LocalidadEntity> pageResult;
		if (query != null && !query.trim().isEmpty()) {
			pageResult = localidadRepository.findByDenominacionContaining(query, pageable);
		} else {
			pageResult = localidadRepository.findAll(pageable);
		}

		List<Localidad> localidades = pageResult.getContent().stream().map(localidadMapper::toDomain).toList();

		return Pagina.of(localidades, paginacion.page(), paginacion.size(), pageResult.getTotalElements());
	}

	@Override
	public Optional<Localidad> getLocalidadPorId(String id) {
		log.debug("Finding locality by id: {}", id);

		return localidadRepository.findById(id).map(localidadMapper::toDomain);
	}

}
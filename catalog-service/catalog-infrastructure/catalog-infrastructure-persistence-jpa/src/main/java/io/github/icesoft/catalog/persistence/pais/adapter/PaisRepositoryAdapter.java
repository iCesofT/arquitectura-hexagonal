package io.github.icesoft.catalog.persistence.pais.adapter;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.pais.model.Pais;
import io.github.icesoft.catalog.domain.pais.port.repository.PaisRepositoryPort;
import io.github.icesoft.catalog.persistence.pais.entities.PaisEntity;
import io.github.icesoft.catalog.persistence.pais.mapper.PaisEntityMapper;
import io.github.icesoft.catalog.persistence.pais.repositories.JpaPaisRepository;
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
 * Implementación JPA del DAO de País
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaisRepositoryAdapter implements PaisRepositoryPort {

	private final JpaPaisRepository paisRepository;
	private final PaisEntityMapper paisMapper;

	@Override
	public Pagina<Pais> getPaisesPaginados(Paginacion paginacion) {
		log.debug("Getting paginated countries with pagination: {}", paginacion);

		Pageable pageable = PageRequest.of(paginacion.page(), paginacion.size(),
				Sort.by(Sort.Direction.ASC, "denominacion"));

		Page<PaisEntity> pageResult = paisRepository.findAll(pageable);

		List<Pais> paises = pageResult.getContent().stream().map(paisMapper::toDomain).toList();

		return Pagina.of(paises, paginacion.page(), paginacion.size(), pageResult.getTotalElements());
	}

	@Override
	public Optional<Pais> getPaisPorId(String id) {
		log.debug("Finding country by id: {}", id);

		return paisRepository.findById(id).map(paisMapper::toDomain);
	}
}
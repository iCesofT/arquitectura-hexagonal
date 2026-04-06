package io.github.icesoft.catalog.persistence.provincia.adapter;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.domain.provincia.port.repository.ProvinciaRepositoryPort;
import io.github.icesoft.catalog.persistence.provincia.entities.ProvinciaEntity;
import io.github.icesoft.catalog.persistence.provincia.mapper.ProvinciaEntityMapper;
import io.github.icesoft.catalog.persistence.provincia.repositories.JpaProvinciaRepository;
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
 * Implementación JPA del DAO de Provincia
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProvinciaRepositoryAdapter implements ProvinciaRepositoryPort {

	private final JpaProvinciaRepository provinciaRepository;
	private final ProvinciaEntityMapper provinciaMapper;

	@Override
	public Pagina<Provincia> getProvinciasPaginadas(Paginacion paginacion) {
		log.debug("Getting paginated provinces with pagination: {}", paginacion);

		Pageable pageable = PageRequest.of(paginacion.page(), paginacion.size(),
				Sort.by(Sort.Direction.ASC, "denominacion"));

		Page<ProvinciaEntity> pageResult = provinciaRepository.findAll(pageable);

		List<Provincia> provincias = pageResult.getContent().stream().map(provinciaMapper::toDomain).toList();

		return Pagina.of(provincias, paginacion.page(), paginacion.size(), pageResult.getTotalElements());
	}

	@Override
	public Optional<Provincia> getProvinciaPorId(String id) {
		log.debug("Finding province by id: {}", id);

		return provinciaRepository.findById(id).map(provinciaMapper::toDomain);
	}

	@Override
	public List<Provincia> getProvinciasByComunidadId(String comunidadId) {
		log.debug("Finding provinces by comunidad autónoma id: {}", comunidadId);

		return provinciaRepository.findByComunidadAutonomaId(comunidadId).stream().map(provinciaMapper::toDomain)
				.toList();
	}

}
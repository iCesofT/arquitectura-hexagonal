package io.github.icesoft.catalog.persistence.tipovia.adapter;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import io.github.icesoft.catalog.domain.tipovia.port.repository.TipoViaRepositoryPort;
import io.github.icesoft.catalog.persistence.tipovia.entities.TipoViaEntity;
import io.github.icesoft.catalog.persistence.tipovia.mapper.TipoViaEntityMapper;
import io.github.icesoft.catalog.persistence.tipovia.repositories.JpaTipoViaRepository;
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
 * Implementación JPA del DAO de TipoVia
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TipoViaAdapter implements TipoViaRepositoryPort {

	private final JpaTipoViaRepository tipoViaRepository;
	private final TipoViaEntityMapper tipoViaMapper;

	@Override
	public Pagina<TipoVia> getTiposViaPaginados(Paginacion paginacion) {
		log.debug("Getting paginated road types with pagination: {}", paginacion);

		Pageable pageable = PageRequest.of(paginacion.page(), paginacion.size(),
				Sort.by(Sort.Direction.ASC, "denominacion"));

		Page<TipoViaEntity> pageResult = tipoViaRepository.findAll(pageable);

		List<TipoVia> tiposVia = pageResult.getContent().stream().map(tipoViaMapper::toDomain).toList();

		return Pagina.of(tiposVia, paginacion.page(), paginacion.size(), pageResult.getTotalElements());
	}

	@Override
	public Optional<TipoVia> getTipoViaPorId(String id) {
		log.debug("Finding road type by id: {}", id);

		return tipoViaRepository.findById(id).map(tipoViaMapper::toDomain);
	}
}
package io.github.icesoft.catalog.application.provincia;

import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.domain.provincia.port.repository.ProvinciaRepositoryPort;
import io.github.icesoft.catalog.domain.provincia.usecase.GetProvinciaByIdUseCase;
import org.springframework.stereotype.Service;

/**
 * Implementación del caso de uso para obtener una provincia por identificador.
 * Coordina la consulta con el puerto de repositorio y maneja la lógica de error
 * cuando la provincia no existe.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Service
public class GetProvinciaByIdUseCaseImpl implements GetProvinciaByIdUseCase {

	private final ProvinciaRepositoryPort repository;

	/**
	 * Constructor de la implementación del caso de uso.
	 * 
	 * @param provinciaDao Puerto de repositorio para operaciones de persistencia de provincias
	 */
	public GetProvinciaByIdUseCaseImpl(ProvinciaRepositoryPort provinciaDao) {
		this.repository = provinciaDao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Provincia execute(String id) {
		return repository.getProvinciaPorId(id).orElseThrow(() -> new NotFoundException("Provincia no encontrada", id));
	}
}
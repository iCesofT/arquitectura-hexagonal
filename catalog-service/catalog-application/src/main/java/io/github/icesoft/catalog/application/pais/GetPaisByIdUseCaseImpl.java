package io.github.icesoft.catalog.application.pais;

import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.pais.model.Pais;
import io.github.icesoft.catalog.domain.pais.port.repository.PaisRepositoryPort;
import io.github.icesoft.catalog.domain.pais.usecase.GetPaisByIdUseCase;
import org.springframework.stereotype.Service;

/**
 * Implementación del caso de uso para obtener un país por identificador.
 * Coordina la consulta con el puerto de repositorio y maneja la lógica de error
 * cuando el país no existe.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Service
public class GetPaisByIdUseCaseImpl implements GetPaisByIdUseCase {

	private final PaisRepositoryPort repository;

	/**
	 * Constructor de la implementación del caso de uso.
	 * 
	 * @param paisDao Puerto de repositorio para operaciones de persistencia de países
	 */
	public GetPaisByIdUseCaseImpl(PaisRepositoryPort paisDao) {
		this.repository = paisDao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Pais execute(String id) {
		return repository.getPaisPorId(id).orElseThrow(() -> new NotFoundException("País no encontrado", id));
	}
}
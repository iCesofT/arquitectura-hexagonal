package io.github.icesoft.catalog.application.comunidadautonoma;

import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.comunidadautonoma.port.repository.ComunidadAutonomaRepositoryPort;
import io.github.icesoft.catalog.domain.comunidadautonoma.usecase.GetComunidadAutonomaByIdUseCase;
import org.springframework.stereotype.Service;

/**
 * Implementación del caso de uso para obtener una comunidad autónoma por
 * identificador. Coordina la consulta con el puerto de repositorio y maneja la
 * lógica de error cuando la comunidad autónoma no existe.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Service
public class GetComunidadAutonomaByIdUseCaseImpl implements GetComunidadAutonomaByIdUseCase {

	private final ComunidadAutonomaRepositoryPort repository;

	/**
	 * Constructor de la implementación del caso de uso.
	 * 
	 * @param comunidadAutonomaDao
	 *            Puerto de repositorio para operaciones de persistencia de
	 *            comunidades autónomas
	 */
	public GetComunidadAutonomaByIdUseCaseImpl(ComunidadAutonomaRepositoryPort comunidadAutonomaDao) {
		this.repository = comunidadAutonomaDao;
	}

	@Override
	public ComunidadAutonoma execute(String id) {
		return repository.getComunidadPorId(id)
				.orElseThrow(() -> new NotFoundException("Comunidad Autónoma no encontrada", id));
	}
}
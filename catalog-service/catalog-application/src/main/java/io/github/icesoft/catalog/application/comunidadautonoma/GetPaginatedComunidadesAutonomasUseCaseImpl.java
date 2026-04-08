package io.github.icesoft.catalog.application.comunidadautonoma;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.comunidadautonoma.port.repository.ComunidadAutonomaRepositoryPort;
import io.github.icesoft.catalog.domain.comunidadautonoma.usecase.GetPaginatedComunidadesAutonomasUseCase;
import org.springframework.stereotype.Service;

/**
 * Implementación del caso de uso para obtener comunidades autónomas paginadas.
 * Coordina la consulta paginada con el puerto de repositorio para optimizar el
 * rendimiento en listados extensos.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Service
public class GetPaginatedComunidadesAutonomasUseCaseImpl implements GetPaginatedComunidadesAutonomasUseCase {

	private final ComunidadAutonomaRepositoryPort repository;

	/**
	 * Constructor de la implementación del caso de uso.
	 * 
	 * @param comunidadAutonomaDao
	 *            Puerto de repositorio para operaciones de persistencia de
	 *            comunidades autónomas
	 */
	public GetPaginatedComunidadesAutonomasUseCaseImpl(ComunidadAutonomaRepositoryPort comunidadAutonomaDao) {
		this.repository = comunidadAutonomaDao;
	}

	@Override
	public Pagina<ComunidadAutonoma> execute(Paginacion paginacion) {
		return repository.getComunidadesPaginadas(paginacion);
	}
}
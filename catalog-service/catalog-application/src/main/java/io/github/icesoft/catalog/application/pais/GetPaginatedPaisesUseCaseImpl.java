package io.github.icesoft.catalog.application.pais;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.pais.model.Pais;
import io.github.icesoft.catalog.domain.pais.port.repository.PaisRepositoryPort;
import io.github.icesoft.catalog.domain.pais.usecase.GetPaginatedPaisesUseCase;
import org.springframework.stereotype.Service;

/**
 * Implementación del caso de uso para obtener países paginados. Coordina la
 * consulta paginada con el puerto de repositorio para optimizar el rendimiento
 * en listados extensos.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Service
public class GetPaginatedPaisesUseCaseImpl implements GetPaginatedPaisesUseCase {

	private final PaisRepositoryPort repository;

	/**
	 * Constructor de la implementación del caso de uso.
	 * 
	 * @param paisDao
	 *            Puerto de repositorio para operaciones de persistencia de países
	 */
	public GetPaginatedPaisesUseCaseImpl(PaisRepositoryPort paisDao) {
		this.repository = paisDao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Pagina<Pais> execute(Paginacion paginacion) {
		return repository.getPaisesPaginados(paginacion);
	}
}
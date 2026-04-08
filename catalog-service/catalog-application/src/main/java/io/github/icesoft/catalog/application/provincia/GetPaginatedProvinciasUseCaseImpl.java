package io.github.icesoft.catalog.application.provincia;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.domain.provincia.port.repository.ProvinciaRepositoryPort;
import io.github.icesoft.catalog.domain.provincia.usecase.GetPaginatedProvinciasUseCase;
import org.springframework.stereotype.Service;

/**
 * Implementación del caso de uso para obtener provincias paginadas. Coordina la
 * consulta paginada con el puerto de repositorio para optimizar el rendimiento
 * en listados extensos.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Service
public class GetPaginatedProvinciasUseCaseImpl implements GetPaginatedProvinciasUseCase {

	private final ProvinciaRepositoryPort repository;

	/**
	 * Constructor de la implementación del caso de uso.
	 * 
	 * @param provinciaDao
	 *            Puerto de repositorio para operaciones de persistencia de
	 *            provincias
	 */
	public GetPaginatedProvinciasUseCaseImpl(ProvinciaRepositoryPort provinciaDao) {
		this.repository = provinciaDao;
	}

	@Override
	public Pagina<Provincia> execute(Paginacion paginacion) {
		return repository.getProvinciasPaginadas(paginacion);
	}
}
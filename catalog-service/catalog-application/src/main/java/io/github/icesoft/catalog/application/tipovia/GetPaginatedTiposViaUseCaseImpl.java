package io.github.icesoft.catalog.application.tipovia;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import io.github.icesoft.catalog.domain.tipovia.port.repository.TipoViaRepositoryPort;
import io.github.icesoft.catalog.domain.tipovia.usecase.GetPaginatedTiposViaUseCase;
import org.springframework.stereotype.Service;

/**
 * Implementación del caso de uso para obtener tipos de vía paginados. Coordina
 * la consulta paginada con el puerto de repositorio para optimizar el
 * rendimiento en listados extensos.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Service
public class GetPaginatedTiposViaUseCaseImpl implements GetPaginatedTiposViaUseCase {

	private final TipoViaRepositoryPort repository;

	/**
	 * Constructor de la implementación del caso de uso.
	 * 
	 * @param tipoViaDao
	 *            Puerto de repositorio para operaciones de persistencia de tipos de
	 *            vía
	 */
	public GetPaginatedTiposViaUseCaseImpl(TipoViaRepositoryPort tipoViaDao) {
		this.repository = tipoViaDao;
	}

	@Override
	public Pagina<TipoVia> execute(Paginacion paginacion) {
		return repository.getTiposViaPaginados(paginacion);
	}
}
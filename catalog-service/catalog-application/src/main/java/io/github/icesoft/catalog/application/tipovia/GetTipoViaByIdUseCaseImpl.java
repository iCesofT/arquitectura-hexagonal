package io.github.icesoft.catalog.application.tipovia;

import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import io.github.icesoft.catalog.domain.tipovia.port.repository.TipoViaRepositoryPort;
import io.github.icesoft.catalog.domain.tipovia.usecase.GetTipoViaByIdUseCase;
import org.springframework.stereotype.Service;

/**
 * Implementación del caso de uso para obtener un tipo de vía por identificador.
 * Coordina la consulta con el puerto de repositorio y maneja la lógica de error
 * cuando el tipo de vía no existe.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Service
public class GetTipoViaByIdUseCaseImpl implements GetTipoViaByIdUseCase {

	private final TipoViaRepositoryPort repository;

	/**
	 * Constructor de la implementación del caso de uso.
	 * 
	 * @param tipoViaDao
	 *            Puerto de repositorio para operaciones de persistencia de tipos de
	 *            vía
	 */
	public GetTipoViaByIdUseCaseImpl(TipoViaRepositoryPort tipoViaDao) {
		this.repository = tipoViaDao;
	}

	@Override
	public TipoVia execute(String id) {
		return repository.getTipoViaPorId(id).orElseThrow(() -> new NotFoundException("Tipo de vía no encontrado", id));
	}
}
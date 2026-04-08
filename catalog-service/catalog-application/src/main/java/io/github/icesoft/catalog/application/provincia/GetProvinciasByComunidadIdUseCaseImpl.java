package io.github.icesoft.catalog.application.provincia;

import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.domain.provincia.port.repository.ProvinciaRepositoryPort;
import io.github.icesoft.catalog.domain.provincia.usecase.GetProvinciasByComunidadIdUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Implementación del caso de uso para obtener las provincias de una comunidad
 * autónoma. Facilita la navegación jerárquica del territorio español desde
 * comunidades hacia sus provincias constituyentes.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Service
public class GetProvinciasByComunidadIdUseCaseImpl implements GetProvinciasByComunidadIdUseCase {

	private final ProvinciaRepositoryPort repository;

	/**
	 * Constructor de la implementación del caso de uso.
	 * 
	 * @param repository
	 *            Puerto de repositorio para operaciones de persistencia de
	 *            provincias
	 */
	public GetProvinciasByComunidadIdUseCaseImpl(ProvinciaRepositoryPort repository) {
		this.repository = repository;
	}

	@Override
	public List<Provincia> execute(String comunidadId) {
		return repository.getProvinciasByComunidadId(comunidadId);
	}
}

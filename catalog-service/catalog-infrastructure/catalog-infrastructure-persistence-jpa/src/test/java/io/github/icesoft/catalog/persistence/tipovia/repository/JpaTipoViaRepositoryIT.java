package io.github.icesoft.catalog.persistence.tipovia.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.icesoft.catalog.persistence.config.TestcontainersConfiguration;
import io.github.icesoft.catalog.persistence.tipovia.entities.TipoViaEntity;
import io.github.icesoft.catalog.persistence.tipovia.repositories.JpaTipoViaRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Import(TestcontainersConfiguration.class)
@DisplayName("Tests de integración de JpaTipoViaRepository contra PostgreSQL real")
class JpaTipoViaRepositoryIT {

	@Autowired
	private JpaTipoViaRepository repository;

	@Test
	@DisplayName("Debería encontrar el tipo de vía 'Calle' por su ID")
	void deberiaEncontrarCallePorId() {
		Optional<TipoViaEntity> result = repository.findById("02");

		assertThat(result).isPresent();
		assertThat(result.get().getClave()).isEqualTo("Calle");
	}

	@Test
	@DisplayName("Debería encontrar un tipo de vía por su clave")
	void deberiaEncontrarPorClave() {
		Optional<TipoViaEntity> result = repository.findByClave("Calle");

		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo("02");
	}

	@Test
	@DisplayName("Debería retornar vacío para un ID inexistente")
	void deberiaRetornarVacioPorIdInexistente() {
		Optional<TipoViaEntity> result = repository.findById("99");

		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("Debería paginar correctamente los tipos de vía")
	void deberiaPaginarTiposVia() {
		Page<TipoViaEntity> page = repository.findAll(PageRequest.of(0, 5, Sort.by("denominacion")));

		assertThat(page.getContent()).isNotEmpty();
		assertThat(page.getTotalElements()).isGreaterThan(0);
	}

	@Test
	@DisplayName("Debería buscar tipos de vía por clave parcial (insensible a mayúsculas)")
	void deberiaBuscarPorClaveParcial() {
		List<TipoViaEntity> result = repository.findByClaveContainingIgnoreCase("calle");

		assertThat(result).isNotEmpty();
		assertThat(result).anyMatch(tv -> tv.getId().equals("02"));
	}

	@Test
	@DisplayName("Debería buscar tipos de vía por clave o denominación")
	void deberiaBuscarPorClaveODenominacion() {
		List<TipoViaEntity> result = repository.findByClaveOrDenominacionContainingIgnoreCase("calle");

		assertThat(result).isNotEmpty();
	}
}

package io.github.icesoft.catalog.persistence.pais.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.icesoft.catalog.persistence.config.TestcontainersConfiguration;
import io.github.icesoft.catalog.persistence.pais.entities.PaisEntity;
import io.github.icesoft.catalog.persistence.pais.repositories.JpaPaisRepository;
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
@DisplayName("Tests de integración de JpaPaisRepository contra PostgreSQL real")
class JpaPaisRepositoryIT {

	@Autowired
	private JpaPaisRepository repository;

	@Test
	@DisplayName("Debería encontrar España por su código numérico ISO")
	void deberiaEncontrarEspaniaPorId() {
		Optional<PaisEntity> result = repository.findById("724");

		assertThat(result).isPresent();
		assertThat(result.get().getAlfa2()).isEqualTo("ES");
		assertThat(result.get().getAlfa3()).isEqualTo("ESP");
		assertThat(result.get().getDenominacion()).isEqualTo("España");
	}

	@Test
	@DisplayName("Debería encontrar un país por código ALFA2")
	void deberiaEncontrarPaisPorAlfa2() {
		Optional<PaisEntity> result = repository.findByAlfa2("ES");

		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo("724");
	}

	@Test
	@DisplayName("Debería encontrar un país por código ALFA3")
	void deberiaEncontrarPaisPorAlfa3() {
		Optional<PaisEntity> result = repository.findByAlfa3("ESP");

		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo("724");
	}

	@Test
	@DisplayName("Debería retornar vacío para un ID inexistente")
	void deberiaRetornarVacioPorIdInexistente() {
		Optional<PaisEntity> result = repository.findById("999");

		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("Debería paginar correctamente los países")
	void deberiaPaginarPaises() {
		Page<PaisEntity> page = repository.findAll(PageRequest.of(0, 10, Sort.by("denominacion")));

		assertThat(page.getContent()).hasSize(10);
		assertThat(page.getTotalElements()).isGreaterThan(10);
	}

	@Test
	@DisplayName("Debería encontrar países por denominación parcial (insensible a mayúsculas)")
	void deberiaEncontrarPaisesPorDenominacionParcial() {
		List<PaisEntity> result = repository.findByDenominacionContainingIgnoreCase("espa");

		assertThat(result).isNotEmpty().anyMatch(p -> p.getId().equals("724"));
	}

	@Test
	@DisplayName("Debería verificar existencia por ALFA2")
	void deberiaVerificarExistenciaPorAlfa2() {
		assertThat(repository.existsByAlfa2("ES")).isTrue();
		assertThat(repository.existsByAlfa2("XX")).isFalse();
	}

	@Test
	@DisplayName("Debería buscar países por cualquier criterio")
	void deberiaBuscarPorCualquierCriterio() {
		List<PaisEntity> result = repository.findByAnyCriteriaContainingIgnoreCase("724");

		assertThat(result).isNotEmpty().anyMatch(p -> p.getId().equals("724"));
	}
}

package io.github.icesoft.catalog.persistence.provincia.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.persistence.comunidadautonoma.entities.ComunidadAutonomaEntity;
import io.github.icesoft.catalog.persistence.provincia.entities.ProvinciaEntity;
import io.github.icesoft.catalog.persistence.provincia.mapper.ProvinciaEntityMapper;
import io.github.icesoft.catalog.persistence.provincia.repositories.JpaProvinciaRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests unitarios para ProvinciaRepositoryAdapter
 */
@DisplayName("Tests de ProvinciaRepositoryAdapter")
@ExtendWith(MockitoExtension.class)
class ProvinciaRepositoryAdapterTest {

	private static final String PROVINCIA_ID = "28";
	private static final String PROVINCIA_DENOMINACION = "Madrid";
	private static final String COMUNIDAD_AUTONOMA_ID = "13";
	private static final String COMUNIDAD_AUTONOMA_DENOMINACION = "Comunidad de Madrid";

	@Mock
	private JpaProvinciaRepository jpaProvinciaRepository;

	@Mock
	private ProvinciaEntityMapper provinciaMapper;

	private ProvinciaRepositoryAdapter adapter;

	@BeforeEach
	void setUp() {
		adapter = new ProvinciaRepositoryAdapter(jpaProvinciaRepository, provinciaMapper);
	}

	@Test
	@DisplayName("Debería obtener una provincia por ID existente")
	void deberiaObtenerProvinciaPorIdExistente() {
		// Arrange
		String provinciaId = "28";
		ComunidadAutonomaEntity comunidadEntity = ComunidadAutonomaEntity.builder().id(COMUNIDAD_AUTONOMA_ID)
				.denominacion(COMUNIDAD_AUTONOMA_DENOMINACION).build();
		ProvinciaEntity provinciaEntity = ProvinciaEntity.builder().id(PROVINCIA_ID)
				.denominacion(PROVINCIA_DENOMINACION).comunidadAutonoma(comunidadEntity).build();
		ComunidadAutonoma comunidad = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_DENOMINACION,
				null, null);
		Provincia provinciaDominio = new Provincia(PROVINCIA_ID, PROVINCIA_DENOMINACION, comunidad);

		when(jpaProvinciaRepository.findById(provinciaId)).thenReturn(Optional.of(provinciaEntity));
		when(provinciaMapper.toDomain(provinciaEntity)).thenReturn(provinciaDominio);

		// Act
		Optional<Provincia> resultado = adapter.getProvinciaPorId(provinciaId);

		// Assert
		assertTrue(resultado.isPresent());
		assertEquals(provinciaDominio, resultado.get());
		verify(jpaProvinciaRepository, times(1)).findById(provinciaId);
		verify(provinciaMapper, times(1)).toDomain(provinciaEntity);
	}

	@Test
	@DisplayName("Debería retornar Optional.empty() cuando la provincia no existe")
	void deberiaRetornarEmptyCuandoProvinciaNoExiste() {
		// Arrange
		String provinciaId = "INVALIDO";
		when(jpaProvinciaRepository.findById(provinciaId)).thenReturn(Optional.empty());

		// Act
		Optional<Provincia> resultado = adapter.getProvinciaPorId(provinciaId);

		// Assert
		assertFalse(resultado.isPresent());
		verify(jpaProvinciaRepository, times(1)).findById(provinciaId);
		verify(provinciaMapper, never()).toDomain(any());
	}

	@Test
	@DisplayName("Debería consultar el repositorio JPA correctamente")
	void deberiaConsultarRepositorioJpaCorrectamente() {
		// Arrange
		String provinciaId = "46";
		when(jpaProvinciaRepository.findById(provinciaId)).thenReturn(Optional.empty());

		// Act
		adapter.getProvinciaPorId(provinciaId);

		// Assert
		verify(jpaProvinciaRepository).findById(provinciaId);
	}

}

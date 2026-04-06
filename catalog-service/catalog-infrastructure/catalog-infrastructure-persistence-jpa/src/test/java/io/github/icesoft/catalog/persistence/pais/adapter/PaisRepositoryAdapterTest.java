package io.github.icesoft.catalog.persistence.pais.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.pais.model.Pais;
import io.github.icesoft.catalog.persistence.pais.entities.PaisEntity;
import io.github.icesoft.catalog.persistence.pais.mapper.PaisEntityMapper;
import io.github.icesoft.catalog.persistence.pais.repositories.JpaPaisRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests unitarios para PaisRepositoryAdapter
 */
@DisplayName("Tests de PaisRepositoryAdapter")
@ExtendWith(MockitoExtension.class)
class PaisRepositoryAdapterTest {

	private static final String PAIS_ID = "724";
	private static final String PAIS_ALFA2 = "ES";
	private static final String PAIS_ALFA3 = "ESP";
	private static final String PAIS_DENOMINACION = "España";

	@Mock
	private JpaPaisRepository jpaPaisRepository;

	@Mock
	private PaisEntityMapper paisMapper;

	private PaisRepositoryAdapter adapter;

	@BeforeEach
	void setUp() {
		adapter = new PaisRepositoryAdapter(jpaPaisRepository, paisMapper);
	}

	@Test
	@DisplayName("Debería obtener un país por ID existente")
	void deberiaObtenerPaisPorIdExistente() {
		// Arrange
		PaisEntity paisEntity = new PaisEntity(PAIS_ID, PAIS_ALFA3, PAIS_ALFA2, PAIS_DENOMINACION);
		Pais paisDominio = new Pais(PAIS_ID, PAIS_DENOMINACION, null, null);

		when(jpaPaisRepository.findById(PAIS_ID)).thenReturn(Optional.of(paisEntity));
		when(paisMapper.toDomain(paisEntity)).thenReturn(paisDominio);

		// Act
		Optional<Pais> resultado = adapter.getPaisPorId(PAIS_ID);

		// Assert
		assertTrue(resultado.isPresent());
		assertEquals(paisDominio, resultado.get());
		verify(jpaPaisRepository, times(1)).findById(PAIS_ID);
		verify(paisMapper, times(1)).toDomain(paisEntity);
	}

	@Test
	@DisplayName("Debería retornar Optional.empty() cuando el país no existe")
	void deberiaRetornarEmptyCuandoPaisNoExiste() {
		// Arrange
		String paisId = "INVALIDO";
		when(jpaPaisRepository.findById(paisId)).thenReturn(Optional.empty());

		// Act
		Optional<Pais> resultado = adapter.getPaisPorId(paisId);

		// Assert
		assertFalse(resultado.isPresent());
		verify(jpaPaisRepository, times(1)).findById(paisId);
		verify(paisMapper, never()).toDomain(any());
	}

	@Test
	@DisplayName("Debería consultar el repositorio JPA correctamente")
	void deberiaConsultarRepositorioJpaCorrectamente() {
		// Arrange
		String paisId = "FRA";
		when(jpaPaisRepository.findById(paisId)).thenReturn(Optional.empty());

		// Act
		adapter.getPaisPorId(paisId);

		// Assert
		verify(jpaPaisRepository).findById(paisId);
	}

}
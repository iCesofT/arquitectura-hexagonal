package io.github.icesoft.catalog.application.pais;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.pais.model.Pais;
import io.github.icesoft.catalog.domain.pais.port.repository.PaisRepositoryPort;
import io.github.icesoft.catalog.domain.pais.usecase.GetPaisByIdUseCase;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests unitarios para GetPaisByIdUseCaseImpl
 */
@DisplayName("Tests de GetPaisByIdUseCase")
@ExtendWith(MockitoExtension.class)
class GetPaisByIdUseCaseImplTest {

	private static final String PAIS_ID = "724";
	private static final String PAIS_ALFA2 = "ES";
	private static final String PAIS_ALFA3 = "ESP";
	private static final String PAIS_DENOMINACION = "España";

	@Mock
	private PaisRepositoryPort paisRepository;

	private GetPaisByIdUseCase useCase;

	@BeforeEach
	void setUp() {
		useCase = new GetPaisByIdUseCaseImpl(paisRepository);
	}

	@Test
	@DisplayName("Debería obtener un país existente")
	void deberiaObtenerPaisExistente() {
		// Arrange
		Pais paisEsperado = new Pais(PAIS_ID, PAIS_DENOMINACION, null, null);
		when(paisRepository.getPaisPorId(PAIS_ID)).thenReturn(Optional.of(paisEsperado));

		// Act
		Pais paisObtenido = useCase.execute(PAIS_ID);

		// Assert
		assertNotNull(paisObtenido);
		assertEquals(PAIS_ID, paisObtenido.id());
		assertEquals(PAIS_DENOMINACION, paisObtenido.nombre());
		verify(paisRepository, times(1)).getPaisPorId(PAIS_ID);
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el país no existe")
	void deberiaLanzarExcepcionCuandoPaisNoExiste() {
		// Arrange
		String paisId = "INVALIDO";
		when(paisRepository.getPaisPorId(paisId)).thenReturn(Optional.empty());

		// Act & Assert
		NotFoundException exception = assertThrows(NotFoundException.class, () -> useCase.execute(paisId));

		assertEquals("País no encontrado", exception.getMessage());
		verify(paisRepository, times(1)).getPaisPorId(paisId);
	}

	@Test
	@DisplayName("Debería no hacer llamadas al repositorio si ya tiene el resultado en caché")
	void deberiaNoHacerLlamadasAlRepositorioSiTieneEnCache() {
		// Arrange
		Pais paisEsperado = new Pais(PAIS_ID, PAIS_DENOMINACION, null, null);
		when(paisRepository.getPaisPorId(PAIS_ID)).thenReturn(Optional.of(paisEsperado));

		// Act
		Pais pais1 = useCase.execute(PAIS_ID);
		Pais pais2 = useCase.execute(PAIS_ID);

		// Assert
		assertEquals(pais1, pais2);
		verify(paisRepository, times(2)).getPaisPorId(PAIS_ID);
	}

}

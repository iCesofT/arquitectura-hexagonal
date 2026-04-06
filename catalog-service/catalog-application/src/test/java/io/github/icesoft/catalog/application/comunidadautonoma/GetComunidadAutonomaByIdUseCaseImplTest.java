package io.github.icesoft.catalog.application.comunidadautonoma;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.comunidadautonoma.port.repository.ComunidadAutonomaRepositoryPort;
import io.github.icesoft.catalog.domain.comunidadautonoma.usecase.GetComunidadAutonomaByIdUseCase;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests unitarios para GetComunidadAutonomaByIdUseCaseImpl
 */
@DisplayName("Tests de GetComunidadAutonomaByIdUseCase")
@ExtendWith(MockitoExtension.class)
class GetComunidadAutonomaByIdUseCaseImplTest {

	private static final String COMUNIDAD_AUTONOMA_ID = "13";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE = "Comunidad de Madrid";

	@Mock
	private ComunidadAutonomaRepositoryPort comunidadAutonomaRepository;

	private GetComunidadAutonomaByIdUseCase useCase;

	@BeforeEach
	void setUp() {
		useCase = new GetComunidadAutonomaByIdUseCaseImpl(comunidadAutonomaRepository);
	}

	@Test
	@DisplayName("Debería obtener una comunidad autónoma existente")
	void deberiaObtenerComunidadAutonomaExistente() {
		// Arrange
		ComunidadAutonoma comunidadEsperada = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE,
				null, null);
		when(comunidadAutonomaRepository.getComunidadPorId(COMUNIDAD_AUTONOMA_ID))
				.thenReturn(Optional.of(comunidadEsperada));

		// Act
		ComunidadAutonoma comunidadObtenida = useCase.execute(COMUNIDAD_AUTONOMA_ID);

		// Assert
		assertNotNull(comunidadObtenida);
		assertEquals(COMUNIDAD_AUTONOMA_ID, comunidadObtenida.id());
		assertEquals(COMUNIDAD_AUTONOMA_NOMBRE, comunidadObtenida.nombre());
		verify(comunidadAutonomaRepository, times(1)).getComunidadPorId(COMUNIDAD_AUTONOMA_ID);
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando la comunidad autónoma no existe")
	void deberiaLanzarExcepcionCuandoComunidadNoExiste() {
		// Arrange
		String comunidadId = "INVALIDO";
		when(comunidadAutonomaRepository.getComunidadPorId(comunidadId)).thenReturn(Optional.empty());

		// Act & Assert
		NotFoundException exception = assertThrows(NotFoundException.class, () -> useCase.execute(comunidadId));

		assertEquals("Comunidad Autónoma no encontrada", exception.getMessage());
		verify(comunidadAutonomaRepository, times(1)).getComunidadPorId(comunidadId);
	}

	@Test
	@DisplayName("Debería verificar que se llamó al repositorio exactamente una vez")
	void deberiaVerificarLlamadasAlRepositorio() {
		// Arrange
		ComunidadAutonoma comunidadEsperada = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE,
				null, null);
		when(comunidadAutonomaRepository.getComunidadPorId(COMUNIDAD_AUTONOMA_ID))
				.thenReturn(Optional.of(comunidadEsperada));

		// Act
		useCase.execute(COMUNIDAD_AUTONOMA_ID);

		// Assert
		verify(comunidadAutonomaRepository, times(1)).getComunidadPorId(COMUNIDAD_AUTONOMA_ID);
	}

}

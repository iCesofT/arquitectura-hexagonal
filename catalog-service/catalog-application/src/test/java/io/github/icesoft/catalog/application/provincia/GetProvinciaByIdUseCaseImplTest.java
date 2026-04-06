package io.github.icesoft.catalog.application.provincia;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.domain.provincia.port.repository.ProvinciaRepositoryPort;
import io.github.icesoft.catalog.domain.provincia.usecase.GetProvinciaByIdUseCase;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests unitarios para GetProvinciaByIdUseCaseImpl
 */
@DisplayName("Tests de GetProvinciaByIdUseCase")
@ExtendWith(MockitoExtension.class)
class GetProvinciaByIdUseCaseImplTest {

	private static final String PROVINCIA_ID = "28";
	private static final String PROVINCIA_DENOMINACION = "Madrid";
	private static final String COMUNIDAD_AUTONOMA_ID = "13";
	private static final String COMUNIDAD_AUTONOMA_DENOMINACION = "Comunidad de Madrid";

	@Mock
	private ProvinciaRepositoryPort provinciaRepository;

	private GetProvinciaByIdUseCase useCase;

	@BeforeEach
	void setUp() {
		useCase = new GetProvinciaByIdUseCaseImpl(provinciaRepository);
	}

	@Test
	@DisplayName("Debería obtener una provincia existente")
	void deberiaObtenerProvinciaExistente() {
		// Arrange
		ComunidadAutonoma comunidad = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_DENOMINACION,
				null, null);
		Provincia provinciaEsperada = new Provincia(PROVINCIA_ID, PROVINCIA_DENOMINACION, comunidad);
		when(provinciaRepository.getProvinciaPorId(PROVINCIA_ID)).thenReturn(Optional.of(provinciaEsperada));

		// Act
		Provincia provinciaObtenida = useCase.execute(PROVINCIA_ID);

		// Assert
		assertNotNull(provinciaObtenida);
		assertEquals(PROVINCIA_ID, provinciaObtenida.id());
		assertEquals(PROVINCIA_DENOMINACION, provinciaObtenida.nombre());
		verify(provinciaRepository, times(1)).getProvinciaPorId(PROVINCIA_ID);
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando la provincia no existe")
	void deberiaLanzarExcepcionCuandoProvinciaNoExiste() {
		// Arrange
		String provinciaId = "INVALIDO";
		when(provinciaRepository.getProvinciaPorId(provinciaId)).thenReturn(Optional.empty());

		// Act & Assert
		NotFoundException exception = assertThrows(NotFoundException.class, () -> useCase.execute(provinciaId));

		assertEquals("Provincia no encontrada", exception.getMessage());
		verify(provinciaRepository, times(1)).getProvinciaPorId(provinciaId);
	}

	@Test
	@DisplayName("Debería verificar que se llamó al repositorio exactamente una vez")
	void deberiaVerificarLlamadasAlRepositorio() {
		// Arrange
		ComunidadAutonoma comunidad = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_DENOMINACION,
				null, null);
		Provincia provinciaEsperada = new Provincia(PROVINCIA_ID, PROVINCIA_DENOMINACION, comunidad);
		when(provinciaRepository.getProvinciaPorId(PROVINCIA_ID)).thenReturn(Optional.of(provinciaEsperada));

		// Act
		useCase.execute(PROVINCIA_ID);

		// Assert
		verify(provinciaRepository, times(1)).getProvinciaPorId(PROVINCIA_ID);
	}

}

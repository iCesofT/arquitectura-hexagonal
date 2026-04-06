package io.github.icesoft.catalog.application.tipovia;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import io.github.icesoft.catalog.domain.tipovia.port.repository.TipoViaRepositoryPort;
import io.github.icesoft.catalog.domain.tipovia.usecase.GetTipoViaByIdUseCase;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests unitarios para GetTipoViaByIdUseCaseImpl
 */
@DisplayName("Tests de GetTipoViaByIdUseCase")
@ExtendWith(MockitoExtension.class)
class GetTipoViaByIdUseCaseImplTest {

	private static final String TIPO_VIA_ID = "2";
	private static final String TIPO_VIA_NOMBRE = "Calle";
	private static final String TIPO_VIA_ABREVIATURA = "-calle-c/-";

	@Mock
	private TipoViaRepositoryPort tipoViaRepository;

	private GetTipoViaByIdUseCase useCase;

	@BeforeEach
	void setUp() {
		useCase = new GetTipoViaByIdUseCaseImpl(tipoViaRepository);
	}

	@Test
	@DisplayName("Debería obtener un tipo de vía existente")
	void deberiaObtenerTipoViaExistente() {
		// Arrange
		TipoVia tipoViaEsperado = new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA);
		when(tipoViaRepository.getTipoViaPorId(TIPO_VIA_ID)).thenReturn(Optional.of(tipoViaEsperado));

		// Act
		TipoVia tipoViaObtenido = useCase.execute(TIPO_VIA_ID);

		// Assert
		assertNotNull(tipoViaObtenido);
		assertEquals(TIPO_VIA_ID, tipoViaObtenido.id());
		assertEquals(TIPO_VIA_NOMBRE, tipoViaObtenido.nombre());
		assertEquals(TIPO_VIA_ABREVIATURA, tipoViaObtenido.abreviatura());
		verify(tipoViaRepository, times(1)).getTipoViaPorId(TIPO_VIA_ID);
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el tipo de vía no existe")
	void deberiaLanzarExcepcionCuandoTipoViaNoExiste() {
		// Arrange
		String tipoViaId = "INVALIDO";
		when(tipoViaRepository.getTipoViaPorId(tipoViaId)).thenReturn(Optional.empty());

		// Act & Assert
		NotFoundException exception = assertThrows(NotFoundException.class, () -> useCase.execute(tipoViaId));

		assertEquals("Tipo de vía no encontrado", exception.getMessage());
		verify(tipoViaRepository, times(1)).getTipoViaPorId(tipoViaId);
	}

	@Test
	@DisplayName("Debería verificar que se llamó al repositorio exactamente una vez")
	void deberiaVerificarLlamadasAlRepositorio() {
		// Arrange
		TipoVia tipoViaEsperado = new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA);
		when(tipoViaRepository.getTipoViaPorId(TIPO_VIA_ID)).thenReturn(Optional.of(tipoViaEsperado));

		// Act
		useCase.execute(TIPO_VIA_ID);

		// Assert
		verify(tipoViaRepository, times(1)).getTipoViaPorId(TIPO_VIA_ID);
	}

}

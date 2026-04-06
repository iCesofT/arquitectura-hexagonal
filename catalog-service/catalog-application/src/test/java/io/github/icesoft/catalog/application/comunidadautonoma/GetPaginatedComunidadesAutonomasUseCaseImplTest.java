package io.github.icesoft.catalog.application.comunidadautonoma;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.comunidadautonoma.port.repository.ComunidadAutonomaRepositoryPort;
import io.github.icesoft.catalog.domain.comunidadautonoma.usecase.GetPaginatedComunidadesAutonomasUseCase;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests unitarios para GetPaginatedComunidadesAutonomasUseCaseImpl
 */
@DisplayName("Tests de GetPaginatedComunidadesAutonomasUseCase")
@ExtendWith(MockitoExtension.class)
class GetPaginatedComunidadesAutonomasUseCaseImplTest {

	private static final int PAGE = 0;
	private static final int SIZE = 2;
	private static final String COMUNIDAD_AUTONOMA_ID_1 = "13";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE_1 = "Comunidad de Madrid";
	private static final String COMUNIDAD_AUTONOMA_ID_2 = "09";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE_2 = "Cataluña";

	@Mock
	private ComunidadAutonomaRepositoryPort comunidadAutonomaRepository;

	private GetPaginatedComunidadesAutonomasUseCase useCase;

	@BeforeEach
	void setUp() {
		useCase = new GetPaginatedComunidadesAutonomasUseCaseImpl(comunidadAutonomaRepository);
	}

	@Test
	@DisplayName("Debería obtener comunidades autónomas paginadas")
	void deberiaObtenerComunidadesAutonomasPaginadas() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pagina<ComunidadAutonoma> paginaEsperada = Pagina.of(
				List.of(new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID_1, COMUNIDAD_AUTONOMA_NOMBRE_1, null, null),
						new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID_2, COMUNIDAD_AUTONOMA_NOMBRE_2, null, null)),
				PAGE, SIZE, 2);
		when(comunidadAutonomaRepository.getComunidadesPaginadas(paginacion)).thenReturn(paginaEsperada);

		// Act
		Pagina<ComunidadAutonoma> paginaObtenida = useCase.execute(paginacion);

		// Assert
		assertNotNull(paginaObtenida);
		assertEquals(PAGE, paginaObtenida.getNumeroPagina());
		assertEquals(SIZE, paginaObtenida.getTamanoPagina());
		assertEquals(2, paginaObtenida.getTotalItems());
		assertEquals(2, paginaObtenida.getItems().size());
		assertEquals(COMUNIDAD_AUTONOMA_ID_1, paginaObtenida.getItems().get(0).id());
		assertEquals(COMUNIDAD_AUTONOMA_NOMBRE_1, paginaObtenida.getItems().get(0).nombre());
		assertEquals(COMUNIDAD_AUTONOMA_ID_2, paginaObtenida.getItems().get(1).id());
		assertEquals(COMUNIDAD_AUTONOMA_NOMBRE_2, paginaObtenida.getItems().get(1).nombre());
		verify(comunidadAutonomaRepository, times(1)).getComunidadesPaginadas(paginacion);
	}

	@Test
	@DisplayName("Debería devolver página vacía cuando no hay comunidades autónomas")
	void deberiaDevolverPaginaVaciaCuandoNoHayComunidades() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pagina<ComunidadAutonoma> paginaEsperada = Pagina.of(List.of(), PAGE, SIZE, 0);
		when(comunidadAutonomaRepository.getComunidadesPaginadas(paginacion)).thenReturn(paginaEsperada);

		// Act
		Pagina<ComunidadAutonoma> paginaObtenida = useCase.execute(paginacion);

		// Assert
		assertNotNull(paginaObtenida);
		assertTrue(paginaObtenida.getItems().isEmpty());
		assertEquals(0, paginaObtenida.getTotalItems());
		verify(comunidadAutonomaRepository, times(1)).getComunidadesPaginadas(paginacion);
	}

	@Test
	@DisplayName("Debería obtener página con un elemento")
	void deberiaObtenerPaginaConUnElemento() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pagina<ComunidadAutonoma> paginaEsperada = Pagina.of(
				List.of(new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID_1, COMUNIDAD_AUTONOMA_NOMBRE_1, null, null)), PAGE,
				SIZE, 1);
		when(comunidadAutonomaRepository.getComunidadesPaginadas(paginacion)).thenReturn(paginaEsperada);

		// Act
		Pagina<ComunidadAutonoma> paginaObtenida = useCase.execute(paginacion);

		// Assert
		assertNotNull(paginaObtenida);
		assertEquals(1, paginaObtenida.getTotalItems());
		assertEquals(1, paginaObtenida.getItems().size());
		assertEquals(COMUNIDAD_AUTONOMA_ID_1, paginaObtenida.getItems().get(0).id());
		verify(comunidadAutonomaRepository, times(1)).getComunidadesPaginadas(paginacion);
	}
}

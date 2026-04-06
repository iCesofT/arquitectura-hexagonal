package io.github.icesoft.catalog.application.provincia;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.domain.provincia.port.repository.ProvinciaRepositoryPort;
import io.github.icesoft.catalog.domain.provincia.usecase.GetPaginatedProvinciasUseCase;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests unitarios para GetPaginatedProvinciasUseCaseImpl
 */
@DisplayName("Tests de GetPaginatedProvinciasUseCase")
@ExtendWith(MockitoExtension.class)
public class GetPaginatedProvinciaUseCaseImplTest {

	private static final int PAGE = 0;
	private static final int SIZE = 2;
	private static final String PROVINCIA_ID_1 = "28";
	private static final String PROVINCIA_NOMBRE_1 = "Madrid";
	private static final String PROVINCIA_ID_2 = "08";
	private static final String PROVINCIA_NOMBRE_2 = "Barcelona";
	private static final String COMUNIDAD_AUTONOMA_ID_1 = "13";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE_1 = "Comunidad de Madrid";
	private static final String COMUNIDAD_AUTONOMA_ID_2 = "09";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE_2 = "Cataluña";

	@Mock
	private ProvinciaRepositoryPort provinciaRepository;

	private GetPaginatedProvinciasUseCase useCase;

	@BeforeEach
	void setUp() {
		useCase = new GetPaginatedProvinciasUseCaseImpl(provinciaRepository);
	}

	@Test
	@DisplayName("Debería obtener provincias paginadas")
	void deberiaObtenerProvinciasPaginadas() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		ComunidadAutonoma comunidad1 = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID_1, COMUNIDAD_AUTONOMA_NOMBRE_1, null,
				null);
		ComunidadAutonoma comunidad2 = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID_2, COMUNIDAD_AUTONOMA_NOMBRE_2, null,
				null);
		Pagina<Provincia> paginaEsperada = Pagina
				.of(List.of(new Provincia(PROVINCIA_ID_1, PROVINCIA_NOMBRE_1, comunidad1),
						new Provincia(PROVINCIA_ID_2, PROVINCIA_NOMBRE_2, comunidad2)), PAGE, SIZE, 2);
		when(provinciaRepository.getProvinciasPaginadas(paginacion)).thenReturn(paginaEsperada);

		// Act
		Pagina<Provincia> paginaObtenida = useCase.execute(paginacion);

		// Assert
		assertNotNull(paginaObtenida);
		assertEquals(PAGE, paginaObtenida.getNumeroPagina());
		assertEquals(SIZE, paginaObtenida.getTamanoPagina());
		assertEquals(2, paginaObtenida.getTotalItems());
		assertEquals(2, paginaObtenida.getItems().size());
		assertEquals(PROVINCIA_ID_1, paginaObtenida.getItems().get(0).id());
		assertEquals(PROVINCIA_NOMBRE_1, paginaObtenida.getItems().get(0).nombre());
		assertEquals(COMUNIDAD_AUTONOMA_ID_1, paginaObtenida.getItems().get(0).comunidadAutonoma().id());
		verify(provinciaRepository, times(1)).getProvinciasPaginadas(paginacion);
	}

	@Test
	@DisplayName("Debería devolver página vacía cuando no hay provincias")
	void deberiaDevolverPaginaVaciaCuandoNoHayProvincias() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pagina<Provincia> paginaEsperada = Pagina.of(List.of(), PAGE, SIZE, 0);
		when(provinciaRepository.getProvinciasPaginadas(paginacion)).thenReturn(paginaEsperada);

		// Act
		Pagina<Provincia> paginaObtenida = useCase.execute(paginacion);

		// Assert
		assertNotNull(paginaObtenida);
		assertTrue(paginaObtenida.getItems().isEmpty());
		assertEquals(0, paginaObtenida.getTotalItems());
		verify(provinciaRepository, times(1)).getProvinciasPaginadas(paginacion);
	}
}

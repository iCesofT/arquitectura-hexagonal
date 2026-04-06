package io.github.icesoft.catalog.application.pais;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.pais.model.Pais;
import io.github.icesoft.catalog.domain.pais.port.repository.PaisRepositoryPort;
import io.github.icesoft.catalog.domain.pais.usecase.GetPaginatedPaisesUseCase;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Tests de GetPaginatedPaisesUseCase")
@ExtendWith(MockitoExtension.class)
class GetPaginatedPaisesUseCaseImplTest {

	private static final int PAGE = 0;
	private static final int SIZE = 2;
	private static final String PAIS_ID_1 = "724";
	private static final String PAIS_NOMBRE_1 = "España";
	private static final String PAIS_ID_2 = "250";
	private static final String PAIS_NOMBRE_2 = "Francia";

	@Mock
	private PaisRepositoryPort paisRepository;

	private GetPaginatedPaisesUseCase useCase;

	@BeforeEach
	void setUp() {
		useCase = new GetPaginatedPaisesUseCaseImpl(paisRepository);
	}

	@Test
	@DisplayName("Debería obtener países paginados")
	void deberiaObtenerPaisesPaginados() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pagina<Pais> paginaEsperada = Pagina.of(
				List.of(new Pais(PAIS_ID_1, PAIS_NOMBRE_1, null, null), new Pais(PAIS_ID_2, PAIS_NOMBRE_2, null, null)),
				PAGE, SIZE, 2);
		when(paisRepository.getPaisesPaginados(paginacion)).thenReturn(paginaEsperada);

		// Act
		Pagina<Pais> paginaObtenida = useCase.execute(paginacion);

		// Assert
		assertNotNull(paginaObtenida);
		assertEquals(PAGE, paginaObtenida.getNumeroPagina());
		assertEquals(SIZE, paginaObtenida.getTamanoPagina());
		assertEquals(2, paginaObtenida.getTotalItems());
		assertEquals(2, paginaObtenida.getItems().size());
		assertEquals(PAIS_ID_1, paginaObtenida.getItems().get(0).id());
		assertEquals(PAIS_NOMBRE_1, paginaObtenida.getItems().get(0).nombre());
		verify(paisRepository, times(1)).getPaisesPaginados(paginacion);
	}

	@Test
	@DisplayName("Debería devolver página vacía cuando no hay países")
	void deberiaDevolverPaginaVaciaCuandoNoHayPaises() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pagina<Pais> paginaEsperada = Pagina.of(List.of(), PAGE, SIZE, 0);
		when(paisRepository.getPaisesPaginados(paginacion)).thenReturn(paginaEsperada);

		// Act
		Pagina<Pais> paginaObtenida = useCase.execute(paginacion);

		// Assert
		assertNotNull(paginaObtenida);
		assertTrue(paginaObtenida.getItems().isEmpty());
		assertEquals(0, paginaObtenida.getTotalItems());
		verify(paisRepository, times(1)).getPaisesPaginados(paginacion);
	}
}

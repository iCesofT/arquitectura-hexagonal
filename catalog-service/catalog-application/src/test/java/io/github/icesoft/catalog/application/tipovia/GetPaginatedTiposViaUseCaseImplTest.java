package io.github.icesoft.catalog.application.tipovia;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import io.github.icesoft.catalog.domain.tipovia.port.repository.TipoViaRepositoryPort;
import io.github.icesoft.catalog.domain.tipovia.usecase.GetPaginatedTiposViaUseCase;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests unitarios para GetPaginatedTiposViaUseCaseImpl
 */
@DisplayName("Tests de GetPaginatedTiposViaUseCase")
@ExtendWith(MockitoExtension.class)
class GetPaginatedTiposViaUseCaseImplTest {

	private static final int PAGE = 0;
	private static final int SIZE = 2;
	private static final String TIPO_VIA_ID_1 = "2";
	private static final String TIPO_VIA_NOMBRE_1 = "Calle";
	private static final String TIPO_VIA_ABREVIATURA_1 = "-calle-c/-";
	private static final String TIPO_VIA_ID_2 = "18";
	private static final String TIPO_VIA_NOMBRE_2 = "Avenida";
	private static final String TIPO_VIA_ABREVIATURA_2 = "-avenida-avda-avda.-av.-";

	@Mock
	private TipoViaRepositoryPort tipoViaRepository;

	private GetPaginatedTiposViaUseCase useCase;

	@BeforeEach
	void setUp() {
		useCase = new GetPaginatedTiposViaUseCaseImpl(tipoViaRepository);
	}

	@Test
	@DisplayName("Debería obtener tipos de vía paginados")
	void deberiaObtenerTiposViaPaginados() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pagina<TipoVia> paginaEsperada = Pagina
				.of(List.of(new TipoVia(TIPO_VIA_ID_1, TIPO_VIA_NOMBRE_1, TIPO_VIA_ABREVIATURA_1),
						new TipoVia(TIPO_VIA_ID_2, TIPO_VIA_NOMBRE_2, TIPO_VIA_ABREVIATURA_2)), PAGE, SIZE, 2);
		when(tipoViaRepository.getTiposViaPaginados(paginacion)).thenReturn(paginaEsperada);

		// Act
		Pagina<TipoVia> paginaObtenida = useCase.execute(paginacion);

		// Assert
		assertNotNull(paginaObtenida);
		assertEquals(PAGE, paginaObtenida.getNumeroPagina());
		assertEquals(SIZE, paginaObtenida.getTamanoPagina());
		assertEquals(2, paginaObtenida.getTotalItems());
		assertEquals(2, paginaObtenida.getItems().size());
		assertEquals(TIPO_VIA_ID_1, paginaObtenida.getItems().get(0).id());
		assertEquals(TIPO_VIA_NOMBRE_1, paginaObtenida.getItems().get(0).nombre());
		assertEquals(TIPO_VIA_ABREVIATURA_1, paginaObtenida.getItems().get(0).abreviatura());
		verify(tipoViaRepository, times(1)).getTiposViaPaginados(paginacion);
	}

	@Test
	@DisplayName("Debería devolver página vacía cuando no hay tipos de vía")
	void deberiaDevolverPaginaVaciaCuandoNoHayTiposVia() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pagina<TipoVia> paginaEsperada = Pagina.of(List.of(), PAGE, SIZE, 0);
		when(tipoViaRepository.getTiposViaPaginados(paginacion)).thenReturn(paginaEsperada);

		// Act
		Pagina<TipoVia> paginaObtenida = useCase.execute(paginacion);

		// Assert
		assertNotNull(paginaObtenida);
		assertTrue(paginaObtenida.getItems().isEmpty());
		assertEquals(0, paginaObtenida.getTotalItems());
		verify(tipoViaRepository, times(1)).getTiposViaPaginados(paginacion);
	}

	@Test
	@DisplayName("Debería obtener página con un elemento")
	void deberiaObtenerPaginaConUnElemento() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pagina<TipoVia> paginaEsperada = Pagina
				.of(List.of(new TipoVia(TIPO_VIA_ID_1, TIPO_VIA_NOMBRE_1, TIPO_VIA_ABREVIATURA_1)), PAGE, SIZE, 1);
		when(tipoViaRepository.getTiposViaPaginados(paginacion)).thenReturn(paginaEsperada);

		// Act
		Pagina<TipoVia> paginaObtenida = useCase.execute(paginacion);

		// Assert
		assertNotNull(paginaObtenida);
		assertEquals(1, paginaObtenida.getTotalItems());
		assertEquals(1, paginaObtenida.getItems().size());
		assertEquals(TIPO_VIA_ID_1, paginaObtenida.getItems().get(0).id());
		verify(tipoViaRepository, times(1)).getTiposViaPaginados(paginacion);
	}
}

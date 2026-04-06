package io.github.icesoft.catalog.application.pais.cucumber;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.pais.model.Pais;
import io.github.icesoft.catalog.domain.pais.port.repository.PaisRepositoryPort;
import io.github.icesoft.catalog.domain.pais.usecase.GetPaginatedPaisesUseCase;
import io.github.icesoft.catalog.domain.pais.usecase.GetPaisByIdUseCase;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Step definitions consolidados para los tests de Cucumber de
 * GetPaisByIdUseCase y GetPaginatedPaisesUseCase
 */
public class GetPaginatedPaisesUseCaseStepDefinitions {

	@Mock
	private PaisRepositoryPort paisRepository;

	private GetPaisByIdUseCase getByIdUseCase;
	private GetPaginatedPaisesUseCase getPaginatedUseCase;
	private Pais paisActual;
	private Pagina<Pais> paginaActual;
	private Paginacion paginacionActual;
	private List<Pais> paisesMock;
	private Exception exceptionActual;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		paisesMock = new ArrayList<>();
		paisActual = null;
		paginaActual = null;
		exceptionActual = null;
	}

	@Given("un repositorio de países disponible")
	public void unRepositorioDePaisesDisponible() {
		getByIdUseCase = new io.github.icesoft.catalog.application.pais.GetPaisByIdUseCaseImpl(paisRepository);
		getPaginatedUseCase = new io.github.icesoft.catalog.application.pais.GetPaginatedPaisesUseCaseImpl(
				paisRepository);
	}

	@Given("un país con ID {string} y nombre {string}")
	public void unPaisConIdYNombre(String id, String nombre) {
		Pais pais = new Pais(id, nombre, null, null);
		when(paisRepository.getPaisPorId(id)).thenReturn(Optional.of(pais));
	}

	@Given("una lista de países disponible con {int} elementos")
	public void unaListaDePaisesDisponible(int cantidad) {
		paisesMock.clear();

		// Crear países de ejemplo
		String[][] paises = {{"724", "España"}, {"250", "Francia"}, {"276", "Alemania"}, {"380", "Italia"},
				{"752", "Suecia"}};

		for (int i = 0; i < cantidad && i < paises.length; i++) {
			paisesMock.add(new Pais(paises[i][0], paises[i][1], null, null));
		}
	}

	@Given("que no existe un país con ID {string}")
	public void queNoExisteUnPaisConId(String id) {
		when(paisRepository.getPaisPorId(id)).thenReturn(Optional.empty());
	}

	@When("solicito el país con ID {string}")
	public void solicitoElPaisConId(String id) {
		try {
			paisActual = getByIdUseCase.execute(id);
			exceptionActual = null;
		} catch (Exception e) {
			exceptionActual = e;
			paisActual = null;
		}
	}

	@When("solicito el país con ID {string} dos veces")
	public void solicitoElPaisConIdDosveces(String id) {
		// Primera llamada
		try {
			paisActual = getByIdUseCase.execute(id);
		} catch (Exception e) {
			exceptionActual = e;
		}

		// Segunda llamada
		try {
			Pais pais2 = getByIdUseCase.execute(id);
			paisActual = pais2;
		} catch (Exception e) {
			exceptionActual = e;
		}
	}

	@Given("solicito la página de países {int} con tamaño {int}")
	public void solicitoLaPaginaDePaises(int pagina, int tamano) {
		paginacionActual = new Paginacion(pagina, tamano);
	}

	@When("ejecuto la consulta de países paginados")
	public void ejecutoLaConsultaDePaisesPaginados() {
		Pagina<Pais> paginaEsperada = Pagina.of(paisesMock, paginacionActual.getNumeroPagina(),
				paginacionActual.getTamanoPagina(), paisesMock.size());

		when(paisRepository.getPaisesPaginados(paginacionActual)).thenReturn(paginaEsperada);

		paginaActual = getPaginatedUseCase.execute(paginacionActual);
	}

	@Then("debería obtener el país con nombre {string}")
	public void deberiaObtenerElPaisConNombre(String nombreEsperado) {
		assertNotNull(paisActual, "El país no debería ser nulo");
		assertEquals(nombreEsperado, paisActual.nombre(),
				String.format("El nombre del país debería ser '%s'", nombreEsperado));
	}

	@Then("debería lanzar una excepción de tipo NotFoundException")
	public void debieriaLanzarExcepcionNotFoundException() {
		assertNotNull(exceptionActual, "Debería haber lanzado una excepción");
		assertTrue(exceptionActual instanceof NotFoundException, String.format(
				"La excepción debería ser NotFoundException, pero fue %s", exceptionActual.getClass().getName()));
	}

	@Then("el mensaje de error debería contener {string}")
	public void elMensajeDeErrorDeberiaContener(String mensajeEsperado) {
		assertNotNull(exceptionActual, "No hay excepción para verificar el mensaje");
		assertTrue(exceptionActual.getMessage().contains(mensajeEsperado), String.format(
				"El mensaje debería contener '%s', pero fue '%s'", mensajeEsperado, exceptionActual.getMessage()));
	}

	@Then("el repositorio debería haber sido llamado dos veces")
	public void elRepositorioDeBeriaHaberSidoLlamadoDosveces() {
		verify(paisRepository, times(2)).getPaisPorId(any());
	}

	@Then("debería obtener {int} países")
	public void deberiaObtenerCantidadDePaises(int cantidadEsperada) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(cantidadEsperada, paginaActual.getItems().size(),
				String.format("Debería haber %d países", cantidadEsperada));
	}

	@Then("el número de página de países debería ser {int}")
	public void elNumeroDePaginaDePaisesDeberiaSer(int paginaEsperada) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(paginaEsperada, paginaActual.getNumeroPagina(),
				String.format("El número de página debería ser %d", paginaEsperada));
	}

	@Then("el tamaño de página de países debería ser {int}")
	public void elTamanoDePaginaDePaisesDeberiaSer(int tamanoEsperado) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(tamanoEsperado, paginaActual.getTamanoPagina(),
				String.format("El tamaño de página debería ser %d", tamanoEsperado));
	}

	@Then("el total de elementos debería ser {int}")
	public void elTotalDeElementosDeberiaSer(int totalEsperado) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(totalEsperado, paginaActual.getTotalItems(),
				String.format("El total de elementos debería ser %d", totalEsperado));
	}

	@Then("la lista de países debería estar vacía")
	public void laListaDePaisesDeberiEstarVacia() {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertTrue(paginaActual.getItems().isEmpty(), "La lista de países debería estar vacía");
	}
}

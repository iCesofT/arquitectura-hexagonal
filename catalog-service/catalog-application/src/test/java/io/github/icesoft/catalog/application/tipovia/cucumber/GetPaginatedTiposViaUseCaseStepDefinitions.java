package io.github.icesoft.catalog.application.tipovia.cucumber;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import io.github.icesoft.catalog.domain.tipovia.port.repository.TipoViaRepositoryPort;
import io.github.icesoft.catalog.domain.tipovia.usecase.GetPaginatedTiposViaUseCase;
import io.github.icesoft.catalog.domain.tipovia.usecase.GetTipoViaByIdUseCase;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Step definitions consolidados para los tests de Cucumber de
 * GetTipoViaByIdUseCase y GetPaginatedTiposViaUseCase
 */
public class GetPaginatedTiposViaUseCaseStepDefinitions {

	@Mock
	private TipoViaRepositoryPort tipoViaRepository;

	private GetTipoViaByIdUseCase getByIdUseCase;
	private GetPaginatedTiposViaUseCase getPaginatedUseCase;
	private TipoVia tipoViaActual;
	private Pagina<TipoVia> paginaActual;
	private Paginacion paginacionActual;
	private List<TipoVia> tiposViaMock;
	private Exception exceptionActual;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		tiposViaMock = new ArrayList<>();
		tipoViaActual = null;
		paginaActual = null;
		exceptionActual = null;
	}

	@Given("un repositorio de tipos de vía disponible")
	public void unRepositorioDeTiposDeViaDisponible() {
		getByIdUseCase = new io.github.icesoft.catalog.application.tipovia.GetTipoViaByIdUseCaseImpl(tipoViaRepository);
		getPaginatedUseCase = new io.github.icesoft.catalog.application.tipovia.GetPaginatedTiposViaUseCaseImpl(
				tipoViaRepository);
	}

	@Given("un tipo de vía con ID {string} y nombre {string}")
	public void unTipoDeViaConIdYNombre(String id, String nombre) {
		String abreviatura = nombre.equals("Calle")
				? "C"
				: nombre.equals("Avenida") ? "AV" : nombre.substring(0, 1).toUpperCase();
		TipoVia tipoVia = new TipoVia(id, nombre, abreviatura);
		when(tipoViaRepository.getTipoViaPorId(id)).thenReturn(Optional.of(tipoVia));
	}

	@Given("que no existe un tipo de vía con ID {string}")
	public void queNoExisteUnTipoDeViaConId(String id) {
		when(tipoViaRepository.getTipoViaPorId(id)).thenReturn(Optional.empty());
	}

	@When("solicito el tipo de vía con ID {string}")
	public void solicitoElTipoDeViaConId(String id) {
		try {
			tipoViaActual = getByIdUseCase.execute(id);
			exceptionActual = null;
		} catch (Exception e) {
			exceptionActual = e;
			tipoViaActual = null;
		}
	}

	@Given("una lista de tipos de vía disponible con {int} elementos")
	public void unaListaDeTiposDeViaDisponible(int cantidad) {
		tiposViaMock.clear();

		String[][] tiposVia = {{"01", "Calle", "CL"}, {"02", "Avenida", "AV"}};

		for (int i = 0; i < cantidad && i < tiposVia.length; i++) {
			tiposViaMock.add(new TipoVia(tiposVia[i][0], tiposVia[i][1], tiposVia[i][2]));
		}
	}

	@Given("solicito la página de tipos de vía {int} con tamaño {int}")
	public void solicitoLaPaginaDeTiposDeVia(int pagina, int tamano) {
		paginacionActual = new Paginacion(pagina, tamano);
	}

	@When("ejecuto la consulta de tipos de vía paginados")
	public void ejecutoLaConsultaDeTiposDeViaPaginados() {
		Pagina<TipoVia> paginaEsperada = Pagina.of(tiposViaMock, paginacionActual.getNumeroPagina(),
				paginacionActual.getTamanoPagina(), tiposViaMock.size());

		when(tipoViaRepository.getTiposViaPaginados(paginacionActual)).thenReturn(paginaEsperada);

		paginaActual = getPaginatedUseCase.execute(paginacionActual);
	}

	@Then("debería obtener el tipo de vía con nombre {string}")
	public void deberiaObtenerElTipoDeViaConNombre(String nombreEsperado) {
		assertNotNull(tipoViaActual, "El tipo de vía no debería ser nulo");
		assertEquals(nombreEsperado, tipoViaActual.nombre(),
				String.format("El nombre del tipo de vía debería ser '%s'", nombreEsperado));
	}

	@Then("debería lanzar una excepción de tipo NotFoundException para tipo de vía")
	public void debieriaLanzarExcepcionNotFoundException() {
		assertNotNull(exceptionActual, "Debería haber lanzado una excepción");
		assertTrue(exceptionActual instanceof NotFoundException, String.format(
				"La excepción debería ser NotFoundException, pero fue %s", exceptionActual.getClass().getName()));
	}

	@Then("el mensaje de error para tipo de vía debería contener {string}")
	public void elMensajeDeErrorParaTipoDeViaDeberiaContener(String mensajeEsperado) {
		assertNotNull(exceptionActual, "Se esperaba una excepción pero no se lanzó ninguna");
		assertTrue(exceptionActual.getMessage().contains(mensajeEsperado),
				String.format("El mensaje de la excepción debería contener '%s'", mensajeEsperado));
	}

	@Then("debería obtener {int} tipos de vía")
	public void deberiaObtenerCantidadDeTiposDeVia(int cantidadEsperada) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(cantidadEsperada, paginaActual.getItems().size(),
				String.format("Debería haber %d tipos de vía", cantidadEsperada));
	}

	@Then("el número de página de tipos de vía debería ser {int}")
	public void elNumeroDePaginaDeTiposDeViaDeberiaSer(int paginaEsperada) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(paginaEsperada, paginaActual.getNumeroPagina(),
				String.format("El número de página debería ser %d", paginaEsperada));
	}

	@Then("el tamaño de página de tipos de vía debería ser {int}")
	public void elTamanoDePaginaDeTiposDeViaDeberiaSer(int tamanoEsperado) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(tamanoEsperado, paginaActual.getTamanoPagina(),
				String.format("El tamaño de página debería ser %d", tamanoEsperado));
	}

	@Then("el total de elementos debería ser {int}")
	public void elTotalDeElementosDeberiaSerr(int totalEsperado) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(totalEsperado, paginaActual.getTotalItems(),
				String.format("El total de elementos debería ser %d", totalEsperado));
	}

	@Then("la lista de tipos de vía debería estar vacía")
	public void laListaDeTiposDeViaDebieriaEstarVacia() {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertTrue(paginaActual.getItems().isEmpty(), "La lista de tipos de vía debería estar vacía");
	}
}

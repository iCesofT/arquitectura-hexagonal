package io.github.icesoft.catalog.application.provincia.cucumber;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.icesoft.catalog.domain.common.exception.NotFoundException;
import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.domain.provincia.port.repository.ProvinciaRepositoryPort;
import io.github.icesoft.catalog.domain.provincia.usecase.GetPaginatedProvinciasUseCase;
import io.github.icesoft.catalog.domain.provincia.usecase.GetProvinciaByIdUseCase;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Step definitions consolidados para los tests de Cucumber de
 * GetProvinciaByIdUseCase y GetPaginatedProvinciasUseCase
 */
public class GetPaginatedProvinciasUseCaseStepDefinitions {

	@Mock
	private ProvinciaRepositoryPort provinciaRepository;

	private GetProvinciaByIdUseCase getByIdUseCase;
	private GetPaginatedProvinciasUseCase getPaginatedUseCase;
	private Provincia provinciaActual;
	private Pagina<Provincia> paginaActual;
	private Paginacion paginacionActual;
	private List<Provincia> provinciasMock;
	private Exception exceptionActual;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		provinciasMock = new ArrayList<>();
		provinciaActual = null;
		paginaActual = null;
		exceptionActual = null;
	}

	@Given("un repositorio de provincias disponible")
	public void unRepositorioDeProvinciasDisponible() {
		getByIdUseCase = new io.github.icesoft.catalog.application.provincia.GetProvinciaByIdUseCaseImpl(
				provinciaRepository);
		getPaginatedUseCase = new io.github.icesoft.catalog.application.provincia.GetPaginatedProvinciasUseCaseImpl(
				provinciaRepository);
	}

	@Given("una provincia con ID {string} y nombre {string}")
	public void unaProvinciaConIdYNombre(String id, String nombre) {
		ComunidadAutonoma comunidadAutonoma = new ComunidadAutonoma("01", "Andalucía", null, null);
		Provincia provincia = new Provincia(id, nombre, comunidadAutonoma);
		when(provinciaRepository.getProvinciaPorId(id)).thenReturn(Optional.of(provincia));
	}

	@Given("que no existe una provincia con ID {string}")
	public void queNoExisteUnaProvinciaConId(String id) {
		when(provinciaRepository.getProvinciaPorId(id)).thenReturn(Optional.empty());
	}

	@When("solicito la provincia con ID {string}")
	public void solicitoLaProvinciaConId(String id) {
		try {
			provinciaActual = getByIdUseCase.execute(id);
			exceptionActual = null;
		} catch (Exception e) {
			exceptionActual = e;
			provinciaActual = null;
		}
	}

	@Given("una lista de provincias disponible con {int} elementos")
	public void unaListaDeProvinciasDisponible(int cantidad) {
		provinciasMock.clear();

		ComunidadAutonoma comunidadAutonoma = new ComunidadAutonoma("01", "Andalucía", null, null);

		String[][] provincias = {{"01", "Álava"}, {"02", "Albacete"}, {"03", "Alicante"}};

		for (int i = 0; i < cantidad && i < provincias.length; i++) {
			provinciasMock.add(new Provincia(provincias[i][0], provincias[i][1], comunidadAutonoma));
		}
	}

	@Given("solicito la página de provincias {int} con tamaño {int}")
	public void solicitoLaPaginaDeProvincias(int pagina, int tamano) {
		paginacionActual = new Paginacion(pagina, tamano);
	}

	@When("ejecuto la consulta de provincias paginadas")
	public void ejecutoLaConsultaDeProvinciasPaginadas() {
		Pagina<Provincia> paginaEsperada = Pagina.of(provinciasMock, paginacionActual.getNumeroPagina(),
				paginacionActual.getTamanoPagina(), provinciasMock.size());

		when(provinciaRepository.getProvinciasPaginadas(paginacionActual)).thenReturn(paginaEsperada);

		paginaActual = getPaginatedUseCase.execute(paginacionActual);
	}

	@Then("debería obtener la provincia con nombre {string}")
	public void deberiaObtenerLaProvinciaConNombre(String nombreEsperado) {
		assertNotNull(provinciaActual, "La provincia no debería ser nula");
		assertEquals(nombreEsperado, provinciaActual.nombre(),
				String.format("El nombre de la provincia debería ser '%s'", nombreEsperado));
	}

	@Then("debería lanzar una excepción de tipo NotFoundException para provincia")
	public void debieriaLanzarExcepcionNotFoundException() {
		assertNotNull(exceptionActual, "Debería haber lanzado una excepción");
		assertTrue(exceptionActual instanceof NotFoundException, String.format(
				"La excepción debería ser NotFoundException, pero fue %s", exceptionActual.getClass().getName()));
	}

	@Then("el mensaje de error para provincia debería contener {string}")
	public void elMensajeDeErrorParaProvinciaDeberiaContener(String mensajeEsperado) {
		assertNotNull(exceptionActual, "Se esperaba una excepción pero no se lanzó ninguna");
		assertTrue(exceptionActual.getMessage().contains(mensajeEsperado),
				String.format("El mensaje de la excepción debería contener '%s'", mensajeEsperado));
	}

	@Then("debería obtener {int} provincias")
	public void deberiaObtenerCantidadDeProvincias(int cantidadEsperada) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(cantidadEsperada, paginaActual.getItems().size(),
				String.format("Debería haber %d provincias", cantidadEsperada));
	}

	@Then("el número de página de provincias debería ser {int}")
	public void elNumeroDePaginaDeProvinciasDeberiaSer(int paginaEsperada) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(paginaEsperada, paginaActual.getNumeroPagina(),
				String.format("El número de página debería ser %d", paginaEsperada));
	}

	@Then("el tamaño de página de provincias debería ser {int}")
	public void elTamanoDePaginaDeProvinciasDeberiaSer(int tamanoEsperado) {
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

	@Then("la lista de provincias debería estar vacía")
	public void laListaDeProvinciasDebieriaEstarVacia() {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertTrue(paginaActual.getItems().isEmpty(), "La lista de provincias debería estar vacía");
	}
}

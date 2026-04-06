package io.github.icesoft.catalog.application.comunidadautonoma.cucumber;

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
import io.github.icesoft.catalog.domain.comunidadautonoma.port.repository.ComunidadAutonomaRepositoryPort;
import io.github.icesoft.catalog.domain.comunidadautonoma.usecase.GetComunidadAutonomaByIdUseCase;
import io.github.icesoft.catalog.domain.comunidadautonoma.usecase.GetPaginatedComunidadesAutonomasUseCase;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Step definitions consolidados para los tests de Cucumber de
 * GetComunidadAutonomaByIdUseCase y GetPaginatedComunidadesAutonomasUseCase
 */
public class GetPaginatedComunidadesAutonomasUseCaseStepDefinitions {

	@Mock
	private ComunidadAutonomaRepositoryPort comunidadAutonomaRepository;

	private GetComunidadAutonomaByIdUseCase getByIdUseCase;
	private GetPaginatedComunidadesAutonomasUseCase getPaginatedUseCase;
	private ComunidadAutonoma comunidadAutonomaActual;
	private Pagina<ComunidadAutonoma> paginaActual;
	private Paginacion paginacionActual;
	private List<ComunidadAutonoma> comunidadesAutonomasMock;
	private Exception exceptionActual;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		comunidadesAutonomasMock = new ArrayList<>();
		comunidadAutonomaActual = null;
		paginaActual = null;
		exceptionActual = null;
	}

	@Given("un repositorio de comunidades autónomas disponible")
	public void unRepositorioDeComunidadesAutonomasDisponible() {
		getByIdUseCase = new io.github.icesoft.catalog.application.comunidadautonoma.GetComunidadAutonomaByIdUseCaseImpl(
				comunidadAutonomaRepository);
		getPaginatedUseCase = new io.github.icesoft.catalog.application.comunidadautonoma.GetPaginatedComunidadesAutonomasUseCaseImpl(
				comunidadAutonomaRepository);
	}

	@Given("una comunidad autónoma con ID {string} y nombre {string}")
	public void unaComunidadAutonomaConIdYNombre(String id, String nombre) {
		ComunidadAutonoma comunidadAutonoma = new ComunidadAutonoma(id, nombre, null, null);
		when(comunidadAutonomaRepository.getComunidadPorId(id)).thenReturn(Optional.of(comunidadAutonoma));
	}

	@Given("que no existe una comunidad autónoma con ID {string}")
	public void queNoExisteUnaComunidadAutonomaConId(String id) {
		when(comunidadAutonomaRepository.getComunidadPorId(id)).thenReturn(Optional.empty());
	}

	@When("solicito la comunidad autónoma con ID {string}")
	public void solicitoLaComunidadAutonomaConId(String id) {
		try {
			comunidadAutonomaActual = getByIdUseCase.execute(id);
			exceptionActual = null;
		} catch (Exception e) {
			exceptionActual = e;
			comunidadAutonomaActual = null;
		}
	}

	@Given("una lista de comunidades autónomas disponible con {int} elementos")
	public void unaListaDeComunidadesAutonomasDisponible(int cantidad) {
		comunidadesAutonomasMock.clear();

		String[][] comunidades = {{"01", "Andalucía"}, {"02", "Aragón"}, {"03", "Asturias"}};

		for (int i = 0; i < cantidad && i < comunidades.length; i++) {
			comunidadesAutonomasMock.add(new ComunidadAutonoma(comunidades[i][0], comunidades[i][1], null, null));
		}
	}

	@Given("solicito la página de comunidades autónomas {int} con tamaño {int}")
	public void solicitoLaPaginaDeComunidadesAutonomas(int pagina, int tamano) {
		paginacionActual = new Paginacion(pagina, tamano);
	}

	@When("ejecuto la consulta de comunidades autónomas paginadas")
	public void ejecutoLaConsultaDeComunidadesAutonomasPaginadas() {
		Pagina<ComunidadAutonoma> paginaEsperada = Pagina.of(comunidadesAutonomasMock,
				paginacionActual.getNumeroPagina(), paginacionActual.getTamanoPagina(),
				comunidadesAutonomasMock.size());

		when(comunidadAutonomaRepository.getComunidadesPaginadas(paginacionActual)).thenReturn(paginaEsperada);

		paginaActual = getPaginatedUseCase.execute(paginacionActual);
	}

	@Then("debería obtener {int} comunidades autónomas")
	public void deberiaObtenerCantidadDeComunidadesAutonomas(int cantidadEsperada) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(cantidadEsperada, paginaActual.getItems().size(),
				String.format("Debería haber %d comunidades autónomas", cantidadEsperada));
	}

	@Then("el número de página de comunidades autónomas debería ser {int}")
	public void elNumeroDePaginaDeComunidadesAutonomasDeberiaSer(int paginaEsperada) {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertEquals(paginaEsperada, paginaActual.getNumeroPagina(),
				String.format("El número de página debería ser %d", paginaEsperada));
	}

	@Then("el tamaño de página de comunidades autónomas debería ser {int}")
	public void elTamanoDePaginaDeComunidadesAutonomasDeberiaSer(int tamanoEsperado) {
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

	@Then("la lista de comunidades autónomas debería estar vacía")
	public void laListaDeComunidadesAutonomasDebieriaEstarVacia() {
		assertNotNull(paginaActual, "La página no debería ser nula");
		assertTrue(paginaActual.getItems().isEmpty(), "La lista de comunidades autónomas debería estar vacía");
	}

	@Then("debería obtener la comunidad autónoma con nombre {string}")
	public void deberiaObtenerLaComunidadAutonomaConNombre(String nombreEsperado) {
		assertNotNull(comunidadAutonomaActual, "La comunidad autónoma no debería ser nula");
		assertEquals(nombreEsperado, comunidadAutonomaActual.nombre(),
				String.format("El nombre de la comunidad autónoma debería ser '%s'", nombreEsperado));
	}

	@Then("debería lanzar una excepción de tipo NotFoundException para comunidad autónoma")
	public void debieriaLanzarExcepcionNotFoundException() {
		assertNotNull(exceptionActual, "Debería haber lanzado una excepción");
		assertTrue(exceptionActual instanceof NotFoundException, String.format(
				"La excepción debería ser NotFoundException, pero fue %s", exceptionActual.getClass().getName()));
	}

	@Then("el mensaje de error para comunidad autónoma debería contener {string}")
	public void elMensajeDeErrorDeberiaContener(String mensajeEsperado) {
		assertNotNull(exceptionActual, "Se esperaba una excepción pero no se lanzó ninguna");
		assertTrue(exceptionActual.getMessage().contains(mensajeEsperado),
				String.format("El mensaje de la excepción debería contener '%s'", mensajeEsperado));
	}
}

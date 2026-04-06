package io.github.icesoft.catalog.domain.comunidadautonoma.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests unitarios para la entidad de dominio ComunidadAutonoma
 */
@DisplayName("Tests de la entidad ComunidadAutonoma")
class ComunidadAutonomaTest {

	private static final String COMUNIDAD_AUTONOMA_ID = "13";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE = "Comunidad de Madrid";

	@Test
	@DisplayName("Debería crear una comunidad autónoma válida")
	void deberiaCrearComunidadAutonomaValida() {
		// Arrange & Act
		ComunidadAutonoma comunidad = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE, null,
				null);

		// Assert
		assertEquals(COMUNIDAD_AUTONOMA_ID, comunidad.id());
		assertEquals(COMUNIDAD_AUTONOMA_NOMBRE, comunidad.nombre());
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID es nulo")
	void deberiaLanzarExcepcionCuandoIdEsNulo() {
		// Arrange & Act & Assert
		assertThrows(NullPointerException.class,
				() -> new ComunidadAutonoma(null, COMUNIDAD_AUTONOMA_NOMBRE, null, null));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre es nulo")
	void deberiaLanzarExcepcionCuandoNombreEsNulo() {
		// Arrange & Act & Assert
		assertThrows(NullPointerException.class, () -> new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, null, null, null));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID está vacío")
	void deberiaLanzarExcepcionCuandoIdEstaVacio() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class,
				() -> new ComunidadAutonoma("", COMUNIDAD_AUTONOMA_NOMBRE, null, null));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre está vacío")
	void deberiaLanzarExcepcionCuandoNombreEstaVacio() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class,
				() -> new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, "", null, null));
	}

	@Test
	@DisplayName("Debería ser comparable por su contenido")
	void deberiaSerComparablePorContenido() {
		// Arrange
		ComunidadAutonoma comunidad1 = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE, null,
				null);
		ComunidadAutonoma comunidad2 = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE, null,
				null);

		// Act & Assert
		assertEquals(comunidad1, comunidad2);
	}

	@Test
	@DisplayName("No debería ser igual con diferentes IDs")
	void noDeberiaSerIgualConDiferentesIds() {
		// Arrange
		ComunidadAutonoma comunidad1 = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE, null,
				null);
		ComunidadAutonoma comunidad2 = new ComunidadAutonoma("BAR", COMUNIDAD_AUTONOMA_NOMBRE, null, null);

		// Act & Assert
		assertNotEquals(comunidad1, comunidad2);
	}

}

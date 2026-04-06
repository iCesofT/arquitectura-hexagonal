package io.github.icesoft.catalog.domain.provincia.model;

import static org.junit.jupiter.api.Assertions.*;

import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests unitarios para la entidad de dominio Provincia
 */
@DisplayName("Tests de la entidad Provincia")
class ProvinciaTest {

	private static final String PROVINCIA_ID = "28";
	private static final String PROVINCIA_NOMBRE = "Madrid";
	private static final String COMUNIDAD_AUTONOMA_ID = "13";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE = "Comunidad de Madrid";

	private ComunidadAutonoma comunidadAutonoma;

	@BeforeEach
	void setUp() {
		comunidadAutonoma = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE, null, null);
	}

	@Test
	@DisplayName("Debería crear una provincia válida")
	void deberiaCrearProvinciaValida() {
		// Arrange & Act
		Provincia provincia = new Provincia(PROVINCIA_ID, PROVINCIA_NOMBRE, comunidadAutonoma);

		// Assert
		assertEquals(PROVINCIA_ID, provincia.id());
		assertEquals(PROVINCIA_NOMBRE, provincia.nombre());
		assertEquals(comunidadAutonoma, provincia.comunidadAutonoma());
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID es nulo")
	void deberiaLanzarExcepcionCuandoIdEsNulo() {
		// Arrange & Act & Assert
		assertThrows(NullPointerException.class, () -> new Provincia(null, PROVINCIA_NOMBRE, comunidadAutonoma));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre es nulo")
	void deberiaLanzarExcepcionCuandoNombreEsNulo() {
		// Arrange & Act & Assert
		assertThrows(NullPointerException.class, () -> new Provincia(PROVINCIA_ID, null, comunidadAutonoma));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando la comunidad autónoma es nula")
	void deberiaLanzarExcepcionCuandoComunidadAutonomaEsNula() {
		// Arrange & Act & Assert
		assertThrows(NullPointerException.class, () -> new Provincia(PROVINCIA_ID, PROVINCIA_NOMBRE, null));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID está vacío")
	void deberiaLanzarExcepcionCuandoIdEstaVacio() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Provincia("", PROVINCIA_NOMBRE, comunidadAutonoma));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre está vacío")
	void deberiaLanzarExcepcionCuandoNombreEstaVacio() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Provincia(PROVINCIA_ID, "", comunidadAutonoma));
	}

	@Test
	@DisplayName("Debería ser comparable por su contenido")
	void deberiaSerComparablePorContenido() {
		// Arrange
		Provincia provincia1 = new Provincia(PROVINCIA_ID, PROVINCIA_NOMBRE, comunidadAutonoma);
		Provincia provincia2 = new Provincia(PROVINCIA_ID, PROVINCIA_NOMBRE, comunidadAutonoma);

		// Act & Assert
		assertEquals(provincia1, provincia2);
	}

	@Test
	@DisplayName("No debería ser igual con diferentes IDs")
	void noDeberiaSerIgualConDiferentesIds() {
		// Arrange
		Provincia provincia1 = new Provincia(PROVINCIA_ID, PROVINCIA_NOMBRE, comunidadAutonoma);
		Provincia provincia2 = new Provincia("29", PROVINCIA_NOMBRE, comunidadAutonoma);

		// Act & Assert
		assertNotEquals(provincia1, provincia2);
	}

}

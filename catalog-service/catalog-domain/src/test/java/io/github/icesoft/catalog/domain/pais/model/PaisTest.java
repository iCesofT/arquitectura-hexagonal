package io.github.icesoft.catalog.domain.pais.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests unitarios para la entidad de dominio Pais
 */
@DisplayName("Tests de la entidad Pais")
class PaisTest {

	private static final String PAIS_ID = "724";
	private static final String PAIS_DENOMINACION = "España";
	private static final String PAIS_ID_2 = "250";
	private static final String PAIS_DENOMINACION_2 = "Francia";

	@Test
	@DisplayName("Debería crear un país válido")
	void deberiaCrearPaisValido() {
		// Arrange & Act
		Pais pais = new Pais(PAIS_ID, PAIS_DENOMINACION, null, null);

		// Assert
		assertEquals(PAIS_ID, pais.id());
		assertEquals(PAIS_DENOMINACION, pais.nombre());
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID es nulo")
	void deberiaLanzarExcepcionCuandoIdEsNulo() {
		// Arrange & Act & Assert
		assertThrows(NullPointerException.class, () -> new Pais(null, PAIS_DENOMINACION, null, null));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre es nulo")
	void deberiaLanzarExcepcionCuandoNombreEsNulo() {
		// Arrange & Act & Assert
		assertThrows(NullPointerException.class, () -> new Pais(PAIS_ID, null, null, null));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID está vacío")
	void deberiaLanzarExcepcionCuandoIdEstaVacio() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Pais("", PAIS_DENOMINACION, null, null));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID solo contiene espacios")
	void deberiaLanzarExcepcionCuandoIdSoloEspacios() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Pais("   ", PAIS_DENOMINACION, null, null));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre está vacío")
	void deberiaLanzarExcepcionCuandoNombreEstaVacio() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Pais(PAIS_ID, "", null, null));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre solo contiene espacios")
	void deberiaLanzarExcepcionCuandoNombreSoloEspacios() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Pais(PAIS_ID, "   ", null, null));
	}

	@Test
	@DisplayName("Debería ser comparable por su contenido")
	void deberiaSerComparablePorContenido() {
		// Arrange
		Pais pais1 = new Pais(PAIS_ID, PAIS_DENOMINACION, null, null);
		Pais pais2 = new Pais(PAIS_ID, PAIS_DENOMINACION, null, null);

		// Act & Assert
		assertEquals(pais1, pais2);
	}

	@Test
	@DisplayName("No debería ser igual con diferentes IDs")
	void noDeberiaSerIgualConDiferentesIds() {
		// Arrange
		Pais pais1 = new Pais(PAIS_ID, PAIS_DENOMINACION, null, null);
		Pais pais2 = new Pais(PAIS_ID_2, PAIS_DENOMINACION, null, null);

		// Act & Assert
		assertNotEquals(pais1, pais2);
	}

	@Test
	@DisplayName("No debería ser igual con diferentes nombres")
	void noDeberiaSerIgualConDiferentesNombres() {
		// Arrange
		Pais pais1 = new Pais(PAIS_ID, PAIS_DENOMINACION, null, null);
		Pais pais2 = new Pais(PAIS_ID, PAIS_DENOMINACION_2, null, null);

		// Act & Assert
		assertNotEquals(pais1, pais2);
	}

}

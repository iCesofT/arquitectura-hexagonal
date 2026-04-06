package io.github.icesoft.catalog.domain.localidad.model;

import static org.junit.jupiter.api.Assertions.*;

import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests unitarios para la entidad de dominio Localidad
 */
@DisplayName("Tests de la entidad Localidad")
class LocalidadTest {

	private static final String LOCALIDAD_ID = "01280796";
	private static final String LOCALIDAD_NOMBRE = "Madrid";
	private static final String LOCALIDAD_ID_2 = "01080193";
	private static final String LOCALIDAD_NOMBRE_2 = "Barcelona";
	private static final String PROVINCIA_ID = "28";
	private static final String PROVINCIA_NOMBRE = "Madrid";
	private static final String COMUNIDAD_AUTONOMA_ID = "13";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE = "Comunidad de Madrid";

	private Provincia crearProvincia() {
		ComunidadAutonoma comunidad = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE, null,
				null);
		return new Provincia(PROVINCIA_ID, PROVINCIA_NOMBRE, comunidad);
	}

	@Test
	@DisplayName("Debería crear una localidad válida")
	void deberiaCrearLocalidadValida() {
		// Arrange & Act
		Provincia provincia = crearProvincia();
		Localidad localidad = new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE, provincia);

		// Assert
		assertEquals(LOCALIDAD_ID, localidad.id());
		assertEquals(LOCALIDAD_NOMBRE, localidad.nombre());
		assertEquals(provincia, localidad.provincia());
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID es nulo")
	void deberiaLanzarExcepcionCuandoIdEsNulo() {
		// Arrange & Act & Assert
		Provincia provincia = crearProvincia();
		assertThrows(NullPointerException.class, () -> new Localidad(null, LOCALIDAD_NOMBRE, provincia));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre es nulo")
	void deberiaLanzarExcepcionCuandoNombreEsNulo() {
		// Arrange & Act & Assert
		Provincia provincia = crearProvincia();
		assertThrows(NullPointerException.class, () -> new Localidad(LOCALIDAD_ID, null, provincia));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando la provincia es nula")
	void deberiaLanzarExcepcionCuandoProvinciaEsNula() {
		// Arrange & Act & Assert
		assertThrows(NullPointerException.class, () -> new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE, null));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID está vacío")
	void deberiaLanzarExcepcionCuandoIdEstaVacio() {
		// Arrange & Act & Assert
		Provincia provincia = crearProvincia();
		assertThrows(IllegalArgumentException.class, () -> new Localidad("", LOCALIDAD_NOMBRE, provincia));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID solo contiene espacios")
	void deberiaLanzarExcepcionCuandoIdSoloEspacios() {
		// Arrange & Act & Assert
		Provincia provincia = crearProvincia();
		assertThrows(IllegalArgumentException.class, () -> new Localidad("   ", LOCALIDAD_NOMBRE, provincia));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre está vacío")
	void deberiaLanzarExcepcionCuandoNombreEstaVacio() {
		// Arrange & Act & Assert
		Provincia provincia = crearProvincia();
		assertThrows(IllegalArgumentException.class, () -> new Localidad(LOCALIDAD_ID, "", provincia));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre solo contiene espacios")
	void deberiaLanzarExcepcionCuandoNombreSoloEspacios() {
		// Arrange & Act & Assert
		Provincia provincia = crearProvincia();
		assertThrows(IllegalArgumentException.class, () -> new Localidad(LOCALIDAD_ID, "   ", provincia));
	}

	@Test
	@DisplayName("Debería ser comparable por su contenido")
	void deberiaSerComparablePorContenido() {
		// Arrange
		Provincia provincia = crearProvincia();
		Localidad localidad1 = new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE, provincia);
		Localidad localidad2 = new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE, provincia);

		// Act & Assert
		assertEquals(localidad1, localidad2);
	}

	@Test
	@DisplayName("No debería ser igual con diferentes IDs")
	void noDeberiaSerIgualConDiferentesIds() {
		// Arrange
		Provincia provincia = crearProvincia();
		Localidad localidad1 = new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE, provincia);
		Localidad localidad2 = new Localidad(LOCALIDAD_ID_2, LOCALIDAD_NOMBRE, provincia);

		// Act & Assert
		assertNotEquals(localidad1, localidad2);
	}

	@Test
	@DisplayName("No debería ser igual con diferentes nombres")
	void noDeberiaSerIgualConDiferentesNombres() {
		// Arrange
		Provincia provincia = crearProvincia();
		Localidad localidad1 = new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE, provincia);
		Localidad localidad2 = new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE_2, provincia);

		// Act & Assert
		assertNotEquals(localidad1, localidad2);
	}

	@Test
	@DisplayName("No debería ser igual con diferentes provincias")
	void noDeberiaSerIgualConDiferentesProvincias() {
		// Arrange
		Provincia provincia1 = crearProvincia();
		ComunidadAutonoma comunidad2 = new ComunidadAutonoma("09", "Cataluña", null, null);
		Provincia provincia2 = new Provincia("08", "Barcelona", comunidad2);
		Localidad localidad1 = new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE, provincia1);
		Localidad localidad2 = new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE, provincia2);

		// Act & Assert
		assertNotEquals(localidad1, localidad2);
	}

}

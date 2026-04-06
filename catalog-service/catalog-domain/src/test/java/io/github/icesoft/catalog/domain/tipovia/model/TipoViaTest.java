package io.github.icesoft.catalog.domain.tipovia.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests unitarios para la entidad de dominio TipoVia
 */
@DisplayName("Tests de la entidad TipoVia")
class TipoViaTest {

	private static final String TIPO_VIA_ID = "2";
	private static final String TIPO_VIA_NOMBRE = "Calle";
	private static final String TIPO_VIA_ABREVIATURA = "-calle-c/-";
	private static final String TIPO_VIA_ID_2 = "18";
	private static final String TIPO_VIA_NOMBRE_2 = "Avenida";
	private static final String TIPO_VIA_ABREVIATURA_2 = "-avenida-avda-avda.-av.-";

	@Test
	@DisplayName("Debería crear un tipo de vía válido")
	void deberiaCrearTipoViaValido() {
		// Arrange & Act
		TipoVia tipoVia = new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA);

		// Assert
		assertEquals(TIPO_VIA_ID, tipoVia.id());
		assertEquals(TIPO_VIA_NOMBRE, tipoVia.nombre());
		assertEquals(TIPO_VIA_ABREVIATURA, tipoVia.abreviatura());
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID es nulo")
	void deberiaLanzarExcepcionCuandoIdEsNulo() {
		// Arrange & Act & Assert
		assertThrows(NullPointerException.class, () -> new TipoVia(null, TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre es nulo")
	void deberiaLanzarExcepcionCuandoNombreEsNulo() {
		// Arrange & Act & Assert
		assertThrows(NullPointerException.class, () -> new TipoVia(TIPO_VIA_ID, null, TIPO_VIA_ABREVIATURA));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando la abreviatura es nula")
	void deberiaLanzarExcepcionCuandoAbreviaturaEsNula() {
		// Arrange & Act & Assert
		assertThrows(NullPointerException.class, () -> new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, null));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID está vacío")
	void deberiaLanzarExcepcionCuandoIdEstaVacio() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new TipoVia("", TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el ID solo contiene espacios")
	void deberiaLanzarExcepcionCuandoIdSoloEspacios() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new TipoVia("   ", TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre está vacío")
	void deberiaLanzarExcepcionCuandoNombreEstaVacio() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new TipoVia(TIPO_VIA_ID, "", TIPO_VIA_ABREVIATURA));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando el nombre solo contiene espacios")
	void deberiaLanzarExcepcionCuandoNombreSoloEspacios() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new TipoVia(TIPO_VIA_ID, "   ", TIPO_VIA_ABREVIATURA));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando la abreviatura está vacía")
	void deberiaLanzarExcepcionCuandoAbreviaturaEstaVacia() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, ""));
	}

	@Test
	@DisplayName("Debería lanzar excepción cuando la abreviatura solo contiene espacios")
	void deberiaLanzarExcepcionCuandoAbreviaturaSoloEspacios() {
		// Arrange & Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, "   "));
	}

	@Test
	@DisplayName("Debería ser comparable por su contenido")
	void deberiaSerComparablePorContenido() {
		// Arrange
		TipoVia tipoVia1 = new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA);
		TipoVia tipoVia2 = new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA);

		// Act & Assert
		assertEquals(tipoVia1, tipoVia2);
	}

	@Test
	@DisplayName("No debería ser igual con diferentes IDs")
	void noDeberiaSerIgualConDiferentesIds() {
		// Arrange
		TipoVia tipoVia1 = new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA);
		TipoVia tipoVia2 = new TipoVia(TIPO_VIA_ID_2, TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA);

		// Act & Assert
		assertNotEquals(tipoVia1, tipoVia2);
	}

	@Test
	@DisplayName("No debería ser igual con diferentes nombres")
	void noDeberiaSerIgualConDiferentesNombres() {
		// Arrange
		TipoVia tipoVia1 = new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA);
		TipoVia tipoVia2 = new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE_2, TIPO_VIA_ABREVIATURA);

		// Act & Assert
		assertNotEquals(tipoVia1, tipoVia2);
	}

	@Test
	@DisplayName("No debería ser igual con diferentes abreviaturas")
	void noDeberiaSerIgualConDiferentesAbreviaturas() {
		// Arrange
		TipoVia tipoVia1 = new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA);
		TipoVia tipoVia2 = new TipoVia(TIPO_VIA_ID, TIPO_VIA_NOMBRE, TIPO_VIA_ABREVIATURA_2);

		// Act & Assert
		assertNotEquals(tipoVia1, tipoVia2);
	}

}

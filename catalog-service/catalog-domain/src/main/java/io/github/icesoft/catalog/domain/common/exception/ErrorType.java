package io.github.icesoft.catalog.domain.common.exception;

/**
 * Interfaz que define la estructura de tipos de error en el dominio. Permite
 * categorizar errores con código de estado, título y mensaje de detalle.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface ErrorType {

	/**
	 * Obtiene el código de estado HTTP asociado al tipo de error.
	 * 
	 * @return Código de estado HTTP (ej: 400, 404, 500)
	 */
	int getStatus();

	/**
	 * Obtiene el título del error para mostrar al usuario.
	 * 
	 * @return Título descriptivo del error
	 */
	String getTitle();

	/**
	 * Obtiene el patrón de mensaje de detalle que puede contener placeholders.
	 * 
	 * @return Patrón de mensaje con placeholders para String.format()
	 */
	String getDetail();
}

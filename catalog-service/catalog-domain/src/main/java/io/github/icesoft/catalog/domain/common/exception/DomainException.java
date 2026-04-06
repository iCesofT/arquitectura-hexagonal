package io.github.icesoft.catalog.domain.common.exception;

/**
 * Excepción base para todas las excepciones del dominio.
 * Encapsula información de error estructurada con código de estado HTTP,
 * título y mensaje detallado para facilitar el manejo de errores.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public class DomainException extends RuntimeException {

	private final ErrorType errorType;
	private final String[] args;

	/**
	 * Constructor para crear una excepción de dominio.
	 * 
	 * @param errorType Tipo de error que define status, título y formato de mensaje
	 * @param args Argumentos para formatear el mensaje de detalle
	 */
	public DomainException(ErrorType errorType, String[] args) {
		super(errorType.getTitle());
		this.errorType = errorType;
		this.args = args;
	}

	/**
	 * Obtiene el código de estado HTTP asociado al error.
	 * 
	 * @return Código de estado HTTP
	 */
	public int getStatus() {
		return errorType.getStatus();
	}

	/**
	 * Obtiene el título del error.
	 * 
	 * @return Título del error
	 */
	public String getTitle() {
		return errorType.getTitle();
	}

	/**
	 * Obtiene el mensaje detallado del error con argumentos formateados.
	 * 
	 * @return Mensaje detallado del error
	 */
	public String getDetail() {
		return String.format(this.errorType.getDetail(), (Object[]) this.args);
	}
}

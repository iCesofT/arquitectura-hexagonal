package io.github.icesoft.catalog.domain.common.exception;

/**
 * Excepción lanzada cuando no se encuentra un recurso solicitado. Representa
 * errores HTTP 404 en el contexto del dominio.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public class NotFoundException extends DomainException {

	/**
	 * Constructor para recursos no encontrados con identificador.
	 * 
	 * @param identifier
	 *            Identificador del recurso no encontrado
	 */
	public NotFoundException(String identifier) {
		super(new ErrorType() {
			@Override
			public int getStatus() {
				return 404;
			}

			@Override
			public String getTitle() {
				return "No encontrado";
			}

			@Override
			public String getDetail() {
				return "El recurso solicitado no existe: '%s'";
			}
		}, new String[]{identifier});
	}

	/**
	 * Constructor para recursos no encontrados con mensaje personalizado.
	 * 
	 * @param message
	 *            Mensaje personalizado de error
	 * @param identifier
	 *            Identificador del recurso no encontrado
	 */
	public NotFoundException(String message, String identifier) {
		super(new ErrorType() {
			@Override
			public int getStatus() {
				return 404;
			}
			@Override
			public String getTitle() {
				return message;
			}
			@Override
			public String getDetail() {
				return "El recurso solicitado no existe: '%s'";
			}

		}, new String[]{identifier});
	}

}

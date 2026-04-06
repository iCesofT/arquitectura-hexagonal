package io.github.icesoft.catalog.application.mediator;

/**
 * Interfaz para handlers que procesan requests específicos.
 * Cada handler se especializa en un tipo de request y produce una respuesta del tipo correspondiente.
 * 
 * @param <T> Tipo de request que este handler puede procesar
 * @param <R> Tipo de respuesta que produce este handler
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface RequestHandler<T extends Request<R>, R> {
	
	/**
	 * Procesa un request y produce la respuesta correspondiente.
	 * 
	 * @param request Request a procesar
	 * @return Respuesta del tipo especificado
	 */
	R handle(T request);
}

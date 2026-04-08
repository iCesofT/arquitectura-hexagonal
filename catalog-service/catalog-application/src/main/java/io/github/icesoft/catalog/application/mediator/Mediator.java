package io.github.icesoft.catalog.application.mediator;

/**
 * Interfaz del patrón Mediator para enviar requests y obtener respuestas.
 * Facilita el desacoplamiento entre controladores y casos de uso, permitiendo
 * funcionalidades cross-cutting como logging, validación y transacciones.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface Mediator {

	/**
	 * Envía una solicitud y obtiene la respuesta correspondiente.
	 * 
	 * @param <R>
	 *            Tipo de respuesta esperada
	 * @param request
	 *            Solicitud a procesar que implementa Request<R>
	 * @return Respuesta del tipo especificado
	 * @throws RuntimeException
	 *             si no se encuentra un handler para el request
	 */
	<R> R send(Request<R> request);
}

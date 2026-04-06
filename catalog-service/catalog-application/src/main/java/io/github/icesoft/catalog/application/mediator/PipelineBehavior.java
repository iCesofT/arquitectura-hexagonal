package io.github.icesoft.catalog.application.mediator;

import java.util.function.Supplier;

/**
 * Interfaz para comportamientos de pipeline que se ejecutan alrededor de handlers.
 * Permite implementar funcionalidades cross-cutting como logging, validación,
 * autorización, caching, etc.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
public interface PipelineBehavior {
	
	/**
	 * Maneja un request aplicando lógica adicional antes/después del handler.
	 * 
	 * @param <R> Tipo de respuesta
	 * @param request Request a procesar
	 * @param next Función que invoca el siguiente comportamiento o handler
	 * @return Respuesta procesada
	 */
	<R> R handle(Request<R> request, Supplier<R> next);
}

package io.github.icesoft.catalog.application.mediator;

import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringMediator implements Mediator {

	private final ApplicationContext context;
	private final List<PipelineBehavior> behaviors;

	@Override
	@SuppressWarnings("unchecked")
	public <R> R send(Request<R> request) {
		// Extraer el tipo genérico R del Request<R>
		var requestType = ResolvableType.forClass(request.getClass());
		var requestInterfaces = requestType.getInterfaces();

		// Buscar la interfaz Request<R> y extraer el tipo R
		ResolvableType responseType = ResolvableType.forClass(Object.class);
		for (ResolvableType iface : requestInterfaces) {
			if (iface.getRawClass() == Request.class) {
				ResolvableType[] generics = iface.getGenerics();
				if (generics.length > 0) {
					responseType = generics[0];
				}
				break;
			}
		}

		var handlerType = ResolvableType.forClassWithGenerics(RequestHandler.class, requestType, responseType);

		var handler = (RequestHandler<Request<R>, R>) context.getBeanProvider(handlerType).getObject();

		Supplier<R> invocation = () -> handler.handle(request);

		// Encadenamos behaviors: último en la lista ejecuta más "cerca" del handler
		for (int i = behaviors.size() - 1; i >= 0; i--) {
			var behavior = behaviors.get(i);
			var next = invocation;
			invocation = () -> behavior.handle(request, next);
		}

		return invocation.get();
	}

}

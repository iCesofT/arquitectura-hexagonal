package io.github.icesoft.catalog.infrastructure.api.grpc.mapper;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.localidad.model.Localidad;
import io.github.icesoft.catalog.infrastructure.api.grpc.LocalidadDetalle;
import io.github.icesoft.catalog.infrastructure.api.grpc.LocalidadPageResult;
import io.github.icesoft.catalog.infrastructure.api.grpc.LocalidadResumen;
import io.github.icesoft.catalog.infrastructure.api.grpc.ProvinciaResumen;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre modelos de Localidad y mensajes gRPC
 */
@Component
public class LocalidadGrpcMapper {

	/**
	 * Convierte de modelo de dominio a resumen gRPC
	 */
	public LocalidadResumen toResumen(Localidad localidad) {
		return LocalidadResumen.newBuilder().setId(localidad.id()).setNombre(localidad.nombre()).build();
	}

	/**
	 * Convierte de modelo de dominio a detalle gRPC completo
	 */
	public LocalidadDetalle toDetalle(Localidad localidad) {
		var provinciaResumen = ProvinciaResumen.newBuilder().setId(localidad.provincia().id())
				.setNombre(localidad.provincia().nombre()).build();

		return LocalidadDetalle.newBuilder().setId(localidad.id()).setNombre(localidad.nombre())
				.setProvincia(provinciaResumen).build();
	}

	/**
	 * Convierte de página de dominio a resultado paginado gRPC
	 */
	public LocalidadPageResult toPageResult(Pagina<Localidad> pagina) {
		var builder = LocalidadPageResult.newBuilder().setPage(pagina.page()).setSize(pagina.size())
				.setTotalItems(pagina.totalItems());

		pagina.items().forEach(localidad -> builder.addItems(toResumen(localidad)));

		return builder.build();
	}
}
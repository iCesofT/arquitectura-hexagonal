package io.github.icesoft.catalog.infrastructure.api.grpc.mapper;

import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import io.github.icesoft.catalog.infrastructure.api.grpc.TipoViaDetalle;
import io.github.icesoft.catalog.infrastructure.api.grpc.TipoViaResumen;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre modelos de TipoVia y mensajes gRPC
 */
@Component
public class TipoViaGrpcMapper {

	/**
	 * Convierte de modelo de dominio a resumen gRPC
	 */
	public TipoViaResumen toResumen(TipoVia tipoVia) {
		return TipoViaResumen.newBuilder().setId(tipoVia.id()).setNombre(tipoVia.nombre()).build();
	}

	/**
	 * Convierte de modelo de dominio a detalle gRPC completo nombre = denominacion
	 * (descripción larga), abreviatura = clave (código corto)
	 */
	public TipoViaDetalle toDetalle(TipoVia tipoVia) {
		var builder = TipoViaDetalle.newBuilder().setId(tipoVia.id()).setNombre(tipoVia.abreviatura());

		if (!tipoVia.nombre().trim().isEmpty()) {
			builder.addDenominacion(tipoVia.nombre());
		}
		if (!tipoVia.abreviatura().trim().isEmpty()) {
			builder.addDenominacion(tipoVia.abreviatura());
		}

		return builder.build();
	}
}
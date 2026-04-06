package io.github.icesoft.catalog.infrastructure.api.grpc.mapper;

import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.infrastructure.api.grpc.ComunidadResumen;
import io.github.icesoft.catalog.infrastructure.api.grpc.ProvinciaDetalle;
import io.github.icesoft.catalog.infrastructure.api.grpc.ProvinciaResumen;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre modelos de Provincia y mensajes gRPC
 */
@Component
public class ProvinciaGrpcMapper {

	/**
	 * Convierte de modelo de dominio a resumen gRPC
	 */
	public ProvinciaResumen toResumen(Provincia provincia) {
		return ProvinciaResumen.newBuilder().setId(provincia.id()).setNombre(provincia.nombre()).build();
	}

	/**
	 * Convierte de modelo de dominio a detalle gRPC completo
	 */
	public ProvinciaDetalle toDetalle(Provincia provincia) {
		var comunidadResumen = ComunidadResumen.newBuilder().setId(provincia.comunidadAutonoma().id())
				.setNombre(provincia.comunidadAutonoma().nombre()).build();

		return ProvinciaDetalle.newBuilder().setId(provincia.id()).setNombre(provincia.nombre())
				.setComunidad(comunidadResumen).build();
	}
}
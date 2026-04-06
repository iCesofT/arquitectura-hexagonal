package io.github.icesoft.catalog.infrastructure.api.grpc.mapper;

import io.github.icesoft.catalog.domain.pais.model.Pais;
import io.github.icesoft.catalog.infrastructure.api.grpc.PaisDetalle;
import io.github.icesoft.catalog.infrastructure.api.grpc.PaisResumen;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre modelos de País y mensajes gRPC
 */
@Component
public class PaisGrpcMapper {

	/**
	 * Convierte de modelo de dominio a resumen gRPC
	 */
	public PaisResumen toResumen(Pais pais) {
		return PaisResumen.newBuilder().setId(pais.id()).setNombre(pais.nombre()).build();
	}

	/**
	 * Convierte de modelo de dominio a detalle gRPC completo
	 */
	public PaisDetalle toDetalle(Pais pais) {
		var builder = PaisDetalle.newBuilder().setId(pais.id()).setNombre(pais.nombre());

		if (pais.alfa2() != null) {
			builder.setIsoAlfa2(pais.alfa2());
		}
		if (pais.alfa3() != null) {
			builder.setIsoAlfa3(pais.alfa3());
		}

		return builder.build();
	}
}
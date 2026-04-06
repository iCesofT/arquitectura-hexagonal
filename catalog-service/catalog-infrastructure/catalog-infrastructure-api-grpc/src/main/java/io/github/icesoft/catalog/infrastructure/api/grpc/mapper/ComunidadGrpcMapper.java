package io.github.icesoft.catalog.infrastructure.api.grpc.mapper;

import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.infrastructure.api.grpc.ComunidadDetalle;
import io.github.icesoft.catalog.infrastructure.api.grpc.ComunidadResumen;
import io.github.icesoft.catalog.infrastructure.api.grpc.ProvinciaResumen;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre modelos de ComunidadAutonoma y mensajes gRPC
 */
@Component
public class ComunidadGrpcMapper {

	/**
	 * Convierte de modelo de dominio a resumen gRPC
	 */
	public ComunidadResumen toResumen(ComunidadAutonoma comunidad) {
		return ComunidadResumen.newBuilder().setId(comunidad.id()).setNombre(comunidad.nombre()).build();
	}

	/**
	 * Convierte de modelo de dominio a detalle gRPC completo
	 */
	public ComunidadDetalle toDetalle(ComunidadAutonoma comunidad, List<Provincia> provincias) {
		var builder = ComunidadDetalle.newBuilder().setId(comunidad.id()).setNombre(comunidad.nombre());

		if (comunidad.rcp() != null) {
			builder.setRcp(comunidad.rcp());
		}
		if (comunidad.dir2() != null) {
			builder.setDir2(comunidad.dir2());
		}

		provincias.forEach(provincia -> {
			var provinciaResumen = ProvinciaResumen.newBuilder().setId(provincia.id()).setNombre(provincia.nombre())
					.build();
			builder.addProvincias(provinciaResumen);
		});

		return builder.build();
	}
}

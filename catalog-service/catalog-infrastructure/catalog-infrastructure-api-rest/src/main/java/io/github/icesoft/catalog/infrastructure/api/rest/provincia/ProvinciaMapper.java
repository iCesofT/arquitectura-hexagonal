package io.github.icesoft.catalog.infrastructure.api.rest.provincia;

import io.github.icesoft.catalog.apimodel.model.ProvinciaDetalleDto;
import io.github.icesoft.catalog.apimodel.model.ProvinciaResumenDto;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.infrastructure.api.rest.comunidadautonoma.ComunidadAutonomaMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper para transformaciones entre entidades de dominio Provincia y DTOs de
 * la API REST. Utiliza MapStruct para generar automáticamente las
 * implementaciones de mapeo. Incluye mapeo de la comunidad autónoma asociada a
 * través de ComunidadAutonomaMapper.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ComunidadAutonomaMapper.class})
public interface ProvinciaMapper {

	/**
	 * Convierte una Provincia del dominio a DTO de resumen
	 */
	@Mapping(target = "id", source = "id")
	@Mapping(target = "nombre", source = "nombre")
	ProvinciaResumenDto toResumenDto(Provincia provincia);

	/**
	 * Convierte una Provincia del dominio a DTO de detalle
	 */
	@Mapping(target = "comunidad", source = "comunidadAutonoma")
	ProvinciaDetalleDto toDetalleDto(Provincia provincia);

	/**
	 * Convierte un DTO de resumen a entidad de dominio Nota: comunidadAutonoma no
	 * puede inferirse desde un DTO de resumen
	 */
	@Mapping(target = "comunidadAutonoma", ignore = true)
	Provincia fromResumenDto(ProvinciaResumenDto dto);

	/**
	 * Convierte un DTO de detalle a entidad de dominio
	 */
	@Mapping(target = "comunidadAutonoma", source = "comunidad")
	Provincia fromDetalleDto(ProvinciaDetalleDto dto);
}
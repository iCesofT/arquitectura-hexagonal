package io.github.icesoft.catalog.infrastructure.api.rest.localidad;

import io.github.icesoft.catalog.apimodel.model.LocalidadDetalleDto;
import io.github.icesoft.catalog.apimodel.model.LocalidadResumenDto;
import io.github.icesoft.catalog.domain.localidad.model.Localidad;
import io.github.icesoft.catalog.infrastructure.api.rest.provincia.ProvinciaMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper para transformaciones entre entidades de dominio Localidad y DTOs de la API REST.
 * Utiliza MapStruct para generar automáticamente las implementaciones de mapeo.
 * Incluye mapeo jerárquico completo: localidad -> provincia -> comunidad autónoma.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProvinciaMapper.class})
public interface LocalidadMapper {

	/**
	 * Convierte una Localidad del dominio a DTO de resumen
	 */
	LocalidadResumenDto toResumenDto(Localidad localidad);

	/**
	 * Convierte una Localidad del dominio a DTO de detalle
	 */
	LocalidadDetalleDto toDetalleDto(Localidad localidad);

	/**
	 * Convierte un DTO de resumen a entidad de dominio Nota: provincia no puede
	 * inferirse desde un DTO de resumen
	 */
	@Mapping(target = "provincia", ignore = true)
	Localidad fromResumenDto(LocalidadResumenDto dto);

	/**
	 * Convierte un DTO de detalle a entidad de dominio
	 */
	Localidad fromDetalleDto(LocalidadDetalleDto dto);
}
package io.github.icesoft.catalog.infrastructure.api.rest.tipovia;

import io.github.icesoft.catalog.apimodel.model.TipoViaDetalleDto;
import io.github.icesoft.catalog.apimodel.model.TipoViaResumenDto;
import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper para transformaciones entre entidades de dominio TipoVia y DTOs de la API REST.
 * Utiliza MapStruct para generar automáticamente las implementaciones de mapeo.
 * 
 * <p><strong>Nota importante sobre mapeo de campos:</strong><br>
 * En la API REST, el campo "nombre" representa la clave/abreviatura del tipo de vía,
 * mientras que "denominacion" representa el nombre completo. Esta convención se mantiene
 * por compatibilidad con sistemas externos.</p>
 * 
 * <p>Mapeo de campos:</p>
 * <ul>
 * <li>Dominio.abreviatura → API.nombre (clave corta)</li>
 * <li>Dominio.nombre → API.denominacion (nombre completo)</li>
 * </ul>
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoViaMapper {

	/**
	 * Convierte un TipoVia del dominio a DTO de resumen para la API REST.
	 * El campo "nombre" del DTO corresponde a la abreviatura (clave) del dominio.
	 * 
	 * @param tipoVia Entidad de dominio a convertir
	 * @return DTO de resumen para respuesta de la API
	 */
	@Mapping(target = "nombre", source = "abreviatura")
	TipoViaResumenDto toResumenDto(TipoVia tipoVia);

	/**
	 * Convierte un TipoVia del dominio a DTO de detalle
	 */
	@Mapping(target = "nombre", source = "abreviatura")
	@Mapping(target = "denominacion", expression = "java(java.util.List.of(tipoVia.nombre()))")
	TipoViaDetalleDto toDetalleDto(TipoVia tipoVia);

	/**
	 * Convierte un DTO de resumen a entidad de dominio Nota: el nombre completo no
	 * está disponible en el DTO de resumen
	 */
	@Mapping(target = "abreviatura", source = "nombre")
	@Mapping(target = "nombre", source = "nombre")
	TipoVia fromResumenDto(TipoViaResumenDto dto);

	/**
	 * Convierte un DTO de detalle a entidad de dominio
	 */
	@Mapping(target = "abreviatura", source = "nombre")
	@Mapping(target = "nombre", expression = "java(dto.getDenominacion().isEmpty() ? dto.getNombre() : dto.getDenominacion().get(0))")
	TipoVia fromDetalleDto(TipoViaDetalleDto dto);
}
package io.github.icesoft.catalog.infrastructure.api.rest.comunidadautonoma;

import io.github.icesoft.catalog.apimodel.model.ComunidadDetalleDto;
import io.github.icesoft.catalog.apimodel.model.ComunidadResumenDto;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.infrastructure.api.rest.pais.PaisMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper para transformaciones entre entidades de dominio ComunidadAutonoma y
 * DTOs de la API REST. Utiliza MapStruct para generar automáticamente las
 * implementaciones de mapeo. Maneja la conversión de códigos administrativos
 * españoles RCP y DIR2.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PaisMapper.class})
public interface ComunidadAutonomaMapper {

	/**
	 * Convierte una ComunidadAutonoma del dominio a DTO de resumen
	 */
	@Mapping(target = "id", source = "id")
	@Mapping(target = "nombre", source = "nombre")
	ComunidadResumenDto toResumenDto(ComunidadAutonoma comunidadAutonoma);

	/**
	 * Convierte una ComunidadAutonoma del dominio a DTO de detalle Nota: provincias
	 * no forma parte del modelo de dominio ComunidadAutonoma
	 */
	@Mapping(target = "provincias", expression = "java(java.util.Collections.emptyList())")
	ComunidadDetalleDto toDetalleDto(ComunidadAutonoma comunidadAutonoma);

	/**
	 * Convierte un DTO de resumen a entidad de dominio
	 */
	@Mapping(target = "rcp", ignore = true)
	@Mapping(target = "dir2", ignore = true)
	ComunidadAutonoma fromResumenDto(ComunidadResumenDto dto);

	/**
	 * Convierte un DTO de detalle a entidad de dominio
	 */
	@Mapping(target = "rcp", source = "rcp")
	@Mapping(target = "dir2", source = "dir2")
	ComunidadAutonoma fromDetalleDto(ComunidadDetalleDto dto);
}
package io.github.icesoft.catalog.infrastructure.api.rest.pais;

import io.github.icesoft.catalog.apimodel.model.PaisDetalleDto;
import io.github.icesoft.catalog.apimodel.model.PaisResumenDto;
import io.github.icesoft.catalog.domain.pais.model.Pais;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper para transformaciones entre entidades de dominio Pais y DTOs de la API REST.
 * Utiliza MapStruct para generar automáticamente las implementaciones de mapeo.
 * Convierte entre el modelo de dominio y la representación JSON expuesta por la API.
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaisMapper {

	/**
	 * Convierte un País del dominio a DTO de resumen
	 */
	@Mapping(target = "id", source = "id")
	@Mapping(target = "nombre", source = "nombre")
	PaisResumenDto toResumenDto(Pais pais);

	/**
	 * Convierte un País del dominio a DTO de detalle
	 */
	@Mapping(target = "isoAlfa2", source = "alfa2")
	@Mapping(target = "isoAlfa3", source = "alfa3")
	PaisDetalleDto toDetalleDto(Pais pais);

	/**
	 * Convierte un DTO de resumen a entidad de dominio
	 */
	@Mapping(target = "alfa2", ignore = true)
	@Mapping(target = "alfa3", ignore = true)
	Pais fromResumenDto(PaisResumenDto dto);

	/**
	 * Convierte un DTO de detalle a entidad de dominio
	 */
	@Mapping(target = "alfa2", source = "isoAlfa2")
	@Mapping(target = "alfa3", source = "isoAlfa3")
	Pais fromDetalleDto(PaisDetalleDto dto);
}
package io.github.icesoft.catalog.persistence.tipovia.mapper;

import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import io.github.icesoft.catalog.persistence.tipovia.entities.TipoViaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper entre TipoVia del dominio y TipoViaEntity de persistencia
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoViaEntityMapper {

	/**
	 * Convierte de entidad de persistencia a dominio
	 */
	@Mapping(source = "denominacion", target = "nombre")
	@Mapping(source = "clave", target = "abreviatura")
	TipoVia toDomain(TipoViaEntity entity);

	/**
	 * Convierte de dominio a entidad de persistencia
	 */
	@Mapping(source = "nombre", target = "denominacion")
	@Mapping(source = "abreviatura", target = "clave")
	TipoViaEntity toEntity(TipoVia domain);
}
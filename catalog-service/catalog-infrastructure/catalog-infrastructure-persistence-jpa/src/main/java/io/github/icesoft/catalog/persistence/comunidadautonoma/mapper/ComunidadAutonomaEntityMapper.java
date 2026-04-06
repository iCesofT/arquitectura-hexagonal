package io.github.icesoft.catalog.persistence.comunidadautonoma.mapper;

import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.persistence.comunidadautonoma.entities.ComunidadAutonomaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper entre ComunidadAutonoma del dominio y ComunidadAutonomaEntity de
 * persistencia
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ComunidadAutonomaEntityMapper {

	/**
	 * Convierte de entidad de persistencia a dominio
	 * 
	 * @param entity
	 *            entidad de persistencia
	 * @return entidad de dominio
	 */
	@Mapping(source = "denominacion", target = "nombre")
	ComunidadAutonoma toDomain(ComunidadAutonomaEntity entity);

	/**
	 * Convierte de dominio a entidad de persistencia
	 */
	@Mapping(source = "nombre", target = "denominacion")
	ComunidadAutonomaEntity toEntity(ComunidadAutonoma domain);
}
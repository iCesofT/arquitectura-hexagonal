package io.github.icesoft.catalog.persistence.provincia.mapper;

import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.persistence.comunidadautonoma.mapper.ComunidadAutonomaEntityMapper;
import io.github.icesoft.catalog.persistence.provincia.entities.ProvinciaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper entre Provincia del dominio y ProvinciaEntity de persistencia
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ComunidadAutonomaEntityMapper.class})
public interface ProvinciaEntityMapper {

	/**
	 * Convierte de entidad de persistencia a dominio
	 * 
	 * @param entity
	 *            entidad de persistencia
	 * @return entidad de dominio
	 */
	@Mapping(source = "entity.denominacion", target = "nombre")
	@Mapping(source = "entity.comunidadAutonoma", target = "comunidadAutonoma")
	Provincia toDomain(ProvinciaEntity entity);

	/**
	 * Convierte de dominio a entidad de persistencia
	 */
	@Mapping(source = "nombre", target = "denominacion")
	ProvinciaEntity toEntity(Provincia domain);
}
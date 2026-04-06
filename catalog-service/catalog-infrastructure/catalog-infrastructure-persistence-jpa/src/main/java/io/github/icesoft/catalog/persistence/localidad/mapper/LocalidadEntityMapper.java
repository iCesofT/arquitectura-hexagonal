package io.github.icesoft.catalog.persistence.localidad.mapper;

import io.github.icesoft.catalog.domain.localidad.model.Localidad;
import io.github.icesoft.catalog.persistence.localidad.entities.LocalidadEntity;
import io.github.icesoft.catalog.persistence.provincia.mapper.ProvinciaEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper entre Localidad del dominio y LocalidadEntity de persistencia
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProvinciaEntityMapper.class})
public interface LocalidadEntityMapper {

	/**
	 * Convierte de entidad de persistencia a dominio
	 * 
	 * @param entity
	 *            entidad de persistencia
	 * @return entidad de dominio
	 */
	@Mapping(source = "entity.denominacion", target = "nombre")
	@Mapping(source = "entity.provincia", target = "provincia")
	Localidad toDomain(LocalidadEntity entity);

	/**
	 * Convierte de dominio a entidad de persistencia
	 */
	@Mapping(source = "nombre", target = "denominacion")
	LocalidadEntity toEntity(Localidad domain);
}
package io.github.icesoft.catalog.persistence.pais.mapper;

import io.github.icesoft.catalog.domain.pais.model.Pais;
import io.github.icesoft.catalog.persistence.pais.entities.PaisEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper entre objetos de dominio País y entidades JPA PaisEntity.
 * Utiliza MapStruct para generar automáticamente las conversiones
 * bidireccionales entre la capa de dominio y la capa de persistencia.
 * 
 * <p>Mantiene la separación entre el modelo de dominio puro y
 * las entidades anotadas con JPA, permitiendo que cada capa evolucione
 * independientemente.</p>
 * 
 * @author Francisco Javier Ahijado &lt;icesoft@icesoft.blog&gt;
 * @since 1.0.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaisEntityMapper {

	/**
	 * Convierte de entidad de persistencia a dominio
	 */
	@Mapping(source = "denominacion", target = "nombre")
	Pais toDomain(PaisEntity entity);

	/**
	 * Convierte de dominio a entidad de persistencia
	 */
	@Mapping(source = "nombre", target = "denominacion")
	PaisEntity toEntity(Pais domain);
}
package io.github.icesoft.catalog.persistence.tipovia.repositories;

import io.github.icesoft.catalog.persistence.tipovia.entities.TipoViaEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTipoViaRepository extends JpaRepository<TipoViaEntity, String> {

	/**
	 * Busca un tipo de vía por clave
	 */
	Optional<TipoViaEntity> findByClave(String clave);

	/**
	 * Busca un tipo de vía por denominación
	 */
	Optional<TipoViaEntity> findByDenominacion(String denominacion);

	/**
	 * Busca tipos de vía que contengan la clave especificada
	 */
	@Query("SELECT tv FROM TipoViaEntity tv WHERE UPPER(tv.clave) LIKE UPPER(CONCAT('%', :clave, '%'))")
	List<TipoViaEntity> findByClaveContainingIgnoreCase(@Param("clave") String clave);

	/**
	 * Busca tipos de vía que contengan la denominación especificada
	 */
	@Query("SELECT tv FROM TipoViaEntity tv WHERE UPPER(tv.denominacion) LIKE UPPER(CONCAT('%', :denominacion, '%'))")
	List<TipoViaEntity> findByDenominacionContainingIgnoreCase(@Param("denominacion") String denominacion);

	/**
	 * Busca tipos de vía que contengan la clave o denominación especificada
	 */
	@Query("SELECT tv FROM TipoViaEntity tv WHERE UPPER(tv.clave) LIKE UPPER(CONCAT('%', :criteria, '%')) "
			+ "OR UPPER(tv.denominacion) LIKE UPPER(CONCAT('%', :criteria, '%'))")
	List<TipoViaEntity> findByClaveOrDenominacionContainingIgnoreCase(@Param("criteria") String criteria);
}
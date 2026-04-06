package io.github.icesoft.catalog.persistence.comunidadautonoma.repository;

import io.github.icesoft.catalog.persistence.comunidadautonoma.entities.ComunidadAutonomaEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaComunidadAutonomaRepository extends JpaRepository<ComunidadAutonomaEntity, String> {

	/**
	 * Busca una comunidad autónoma por denominación
	 */
	Optional<ComunidadAutonomaEntity> findByDenominacion(String denominacion);

	/**
	 * Busca comunidades autónomas que contengan la denominación especificada
	 */
	@Query("SELECT ca FROM ComunidadAutonomaEntity ca WHERE UPPER(ca.denominacion) LIKE UPPER(CONCAT('%', :denominacion, '%'))")
	List<ComunidadAutonomaEntity> findByDenominacionContainingIgnoreCase(@Param("denominacion") String denominacion);

	/**
	 * Busca por código RCP
	 */
	Optional<ComunidadAutonomaEntity> findByRcp(String rcp);

	/**
	 * Busca por código DIR2
	 */
	Optional<ComunidadAutonomaEntity> findByDir2(String dir2);
}
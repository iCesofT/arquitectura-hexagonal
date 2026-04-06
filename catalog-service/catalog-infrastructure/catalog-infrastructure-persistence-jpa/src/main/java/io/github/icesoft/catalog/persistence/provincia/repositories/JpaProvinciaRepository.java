package io.github.icesoft.catalog.persistence.provincia.repositories;

import io.github.icesoft.catalog.persistence.comunidadautonoma.entities.ComunidadAutonomaEntity;
import io.github.icesoft.catalog.persistence.provincia.entities.ProvinciaEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProvinciaRepository extends JpaRepository<ProvinciaEntity, String> {

	/**
	 * Busca una provincia por denominación
	 */
	Optional<ProvinciaEntity> findByDenominacion(String denominacion);

	/**
	 * Busca provincias que contengan la denominación especificada (con JOIN FETCH)
	 */
	@Query("SELECT p FROM ProvinciaEntity p JOIN FETCH p.comunidadAutonoma WHERE UPPER(p.denominacion) LIKE UPPER(CONCAT('%', :denominacion, '%'))")
	List<ProvinciaEntity> findByDenominacionContainingIgnoreCase(@Param("denominacion") String denominacion);

	/**
	 * Busca todas las provincias de una comunidad autónoma
	 */
	List<ProvinciaEntity> findByComunidadAutonoma(ComunidadAutonomaEntity comunidadAutonoma);

	/**
	 * Busca todas las provincias de una comunidad autónoma por ID (con JOIN FETCH)
	 */
	@Query("SELECT p FROM ProvinciaEntity p JOIN FETCH p.comunidadAutonoma WHERE p.comunidadAutonoma.id = :comunidadAutonomaId")
	List<ProvinciaEntity> findByComunidadAutonomaId(@Param("comunidadAutonomaId") String comunidadAutonomaId);

	/**
	 * Busca todas las provincias con sus comunidades autónomas cargadas (evita N+1)
	 */
	@Query("SELECT p FROM ProvinciaEntity p JOIN FETCH p.comunidadAutonoma")
	List<ProvinciaEntity> findAllWithComunidadAutonoma();

	/**
	 * Busca provincias por denominación y comunidad autónoma (con JOIN FETCH)
	 */
	@Query("SELECT p FROM ProvinciaEntity p JOIN FETCH p.comunidadAutonoma WHERE UPPER(p.denominacion) LIKE UPPER(CONCAT('%', :denominacion, '%')) "
			+ "AND p.comunidadAutonoma.id = :comunidadAutonomaId")
	List<ProvinciaEntity> findByDenominacionAndComunidadAutonomaId(@Param("denominacion") String denominacion,
			@Param("comunidadAutonomaId") String comunidadAutonomaId);

}
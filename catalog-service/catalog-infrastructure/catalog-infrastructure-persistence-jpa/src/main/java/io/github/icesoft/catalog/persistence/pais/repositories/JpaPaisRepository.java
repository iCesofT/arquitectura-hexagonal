package io.github.icesoft.catalog.persistence.pais.repositories;

import io.github.icesoft.catalog.persistence.pais.entities.PaisEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPaisRepository extends JpaRepository<PaisEntity, String> {

	/**
	 * Busca un país por código ALFA3
	 */
	Optional<PaisEntity> findByAlfa3(String alfa3);

	/**
	 * Busca un país por código ALFA2
	 */
	Optional<PaisEntity> findByAlfa2(String alfa2);

	/**
	 * Busca un país por denominación
	 */
	Optional<PaisEntity> findByDenominacion(String denominacion);

	/**
	 * Busca países que contengan la denominación especificada
	 */
	@Query("SELECT p FROM PaisEntity p WHERE UPPER(p.denominacion) LIKE UPPER(CONCAT('%', :denominacion, '%'))")
	List<PaisEntity> findByDenominacionContainingIgnoreCase(@Param("denominacion") String denominacion);

	/**
	 * Busca países por cualquier criterio (ID, ALFA3, ALFA2 o denominación)
	 */
	@Query("SELECT p FROM PaisEntity p WHERE UPPER(p.id) LIKE UPPER(CONCAT('%', :criteria, '%')) "
			+ "OR UPPER(p.alfa3) LIKE UPPER(CONCAT('%', :criteria, '%')) "
			+ "OR UPPER(p.alfa2) LIKE UPPER(CONCAT('%', :criteria, '%')) "
			+ "OR UPPER(p.denominacion) LIKE UPPER(CONCAT('%', :criteria, '%'))")
	List<PaisEntity> findByAnyCriteriaContainingIgnoreCase(@Param("criteria") String criteria);

	/**
	 * Verifica si existe un país con el código ALFA3 especificado
	 */
	boolean existsByAlfa3(String alfa3);

	/**
	 * Verifica si existe un país con el código ALFA2 especificado
	 */
	boolean existsByAlfa2(String alfa2);
}
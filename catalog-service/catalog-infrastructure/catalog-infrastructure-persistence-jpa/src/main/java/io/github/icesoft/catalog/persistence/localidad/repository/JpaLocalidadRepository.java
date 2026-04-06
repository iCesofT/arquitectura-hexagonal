package io.github.icesoft.catalog.persistence.localidad.repository;

import io.github.icesoft.catalog.persistence.localidad.entities.LocalidadEntity;
import io.github.icesoft.catalog.persistence.provincia.entities.ProvinciaEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLocalidadRepository extends JpaRepository<LocalidadEntity, String> {

	/**
	 * Busca una localidad por denominación y provincia (índice único)
	 */
	Optional<LocalidadEntity> findByDenominacionAndProvincia(String denominacion, ProvinciaEntity provincia);

	/**
	 * Busca una localidad por denominación y ID de provincia (con JOIN FETCH)
	 */
	@Query("SELECT l FROM LocalidadEntity l JOIN FETCH l.provincia WHERE l.denominacion = :denominacion AND l.provincia.id = :provinciaId")
	Optional<LocalidadEntity> findByDenominacionAndProvinciaId(@Param("denominacion") String denominacion,
			@Param("provinciaId") String provinciaId);

	/**
	 * Busca localidades que contengan la denominación especificada (con JOIN FETCH)
	 */
	@Query("SELECT l FROM LocalidadEntity l JOIN FETCH l.provincia WHERE UPPER(l.denominacion) LIKE UPPER(CONCAT('%', :denominacion, '%'))")
	List<LocalidadEntity> findByDenominacionContainingIgnoreCase(@Param("denominacion") String denominacion);

	/**
	 * Busca todas las localidades de una provincia
	 */
	List<LocalidadEntity> findByProvincia(ProvinciaEntity provincia);

	/**
	 * Busca todas las localidades de una provincia por ID (con JOIN FETCH)
	 */
	@Query("SELECT l FROM LocalidadEntity l JOIN FETCH l.provincia WHERE l.provincia.id = :provinciaId")
	List<LocalidadEntity> findByProvinciaId(@Param("provinciaId") String provinciaId);

	/**
	 * Busca todas las localidades con sus provincias cargadas (evita N+1)
	 */
	@Query("SELECT l FROM LocalidadEntity l JOIN FETCH l.provincia")
	List<LocalidadEntity> findAllWithProvincia();

	/**
	 * Busca todas las localidades con provincia y comunidad autónoma cargadas
	 * (evita N+1)
	 */
	@Query("SELECT l FROM LocalidadEntity l JOIN FETCH l.provincia p JOIN FETCH p.comunidadAutonoma")
	List<LocalidadEntity> findAllWithProvinciaAndComunidadAutonoma();

	/**
	 * Busca localidades por denominación y provincia con múltiples join fetch
	 */
	@Query("SELECT l FROM LocalidadEntity l JOIN FETCH l.provincia p JOIN FETCH p.comunidadAutonoma WHERE UPPER(l.denominacion) LIKE UPPER(CONCAT('%', :denominacion, '%')) "
			+ "AND l.provincia.id = :provinciaId")
	List<LocalidadEntity> findByDenominacionAndProvinciaIdWithFullData(@Param("denominacion") String denominacion,
			@Param("provinciaId") String provinciaId);

	/**
	 * Busca todas las localidades de una comunidad autónoma (con JOIN FETCH)
	 */
	@Query("SELECT l FROM LocalidadEntity l JOIN FETCH l.provincia p JOIN FETCH p.comunidadAutonoma WHERE l.provincia.comunidadAutonoma.id = :comunidadAutonomaId")
	List<LocalidadEntity> findByComunidadAutonomaId(@Param("comunidadAutonomaId") String comunidadAutonomaId);

	/**
	 * Evita N+1 usando @EntityGraph - carga provincia de forma lazy pero detenida
	 * en una sola consulta Alternativa a JOIN FETCH que permite mejor control sobre
	 * qué relaciones cargar
	 */
	@EntityGraph(attributePaths = {"provincia"})
	List<LocalidadEntity> findAll();

	/**
	 * Busca localidades de una provincia usando @EntityGraph (sin JOIN FETCH) Más
	 * limpio y mantenible que JOIN FETCH en consultas complejas
	 */
	@EntityGraph(attributePaths = {"provincia"})
	@Query("SELECT l FROM LocalidadEntity l WHERE l.provincia.id = :provinciaId")
	List<LocalidadEntity> findByProvinciaIdWithEntityGraph(@Param("provinciaId") String provinciaId);

	/**
	 * Busca localidades con múltiples relaciones usando @EntityGraph Evita N+1 para
	 * provincia y comunidad autónoma sin usar JOIN FETCH
	 */
	@EntityGraph(attributePaths = {"provincia", "provincia.comunidadAutonoma"})
	@Query("SELECT l FROM LocalidadEntity l WHERE UPPER(l.denominacion) LIKE UPPER(CONCAT('%', :denominacion, '%'))")
	List<LocalidadEntity> findByDenominacionWithEntityGraph(@Param("denominacion") String denominacion);

	/**
	 * Busca localidades en batch: primero carga todas las localidades, luego carga
	 * todas las provincias asociadas en una sola consulta Evita N+1 sin usar JOIN
	 * FETCH ni @EntityGraph
	 */
	@Query("SELECT l FROM LocalidadEntity l")
	@EntityGraph(attributePaths = {"provincia"})
	List<LocalidadEntity> findAllWithBatchLoading();

	/**
	 * Busca un localidad por ID con @EntityGraph Carga la provincia en la misma
	 * consulta
	 */
	@EntityGraph(attributePaths = {"provincia"})
	Optional<LocalidadEntity> findById(String id);

	/**
	 * Busca localidades por denominación similar con paginación
	 */
	@Query("SELECT l FROM LocalidadEntity l JOIN FETCH l.provincia  WHERE UPPER(l.denominacion) LIKE UPPER(CONCAT('%', :denominacion, '%'))")
	Page<LocalidadEntity> findByDenominacionContaining(@Param("denominacion") String denominacion, Pageable pageable);
}
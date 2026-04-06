package io.github.icesoft.catalog.persistence.localidad.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.localidad.model.Localidad;
import io.github.icesoft.catalog.domain.provincia.model.Provincia;
import io.github.icesoft.catalog.persistence.localidad.entities.LocalidadEntity;
import io.github.icesoft.catalog.persistence.localidad.mapper.LocalidadEntityMapper;
import io.github.icesoft.catalog.persistence.localidad.repository.JpaLocalidadRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

/**
 * Tests unitarios para LocalidadRepositoryAdapter
 */
@DisplayName("Tests de LocalidadRepositoryAdapter")
@ExtendWith(MockitoExtension.class)
class LocalidadRepositoryAdapterTest {

	private static final String LOCALIDAD_ID = "01280796";
	private static final String LOCALIDAD_NOMBRE = "Madrid";
	private static final String LOCALIDAD_ID_2 = "01080193";
	private static final String LOCALIDAD_NOMBRE_2 = "Barcelona";
	private static final String PROVINCIA_ID = "28";
	private static final String PROVINCIA_NOMBRE = "Madrid";
	private static final String PROVINCIA_ID_2 = "08";
	private static final String PROVINCIA_NOMBRE_2 = "Barcelona";
	private static final String COMUNIDAD_AUTONOMA_ID = "13";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE = "Comunidad de Madrid";
	private static final String COMUNIDAD_AUTONOMA_ID_2 = "09";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE_2 = "Cataluña";
	private static final int PAGE = 0;
	private static final int SIZE = 10;
	private static final String QUERY = "";

	@Mock
	private JpaLocalidadRepository jpaLocalidadRepository;

	@Mock
	private LocalidadEntityMapper localidadMapper;

	private LocalidadRepositoryAdapter adapter;

	@BeforeEach
	void setUp() {
		adapter = new LocalidadRepositoryAdapter(jpaLocalidadRepository, localidadMapper);
	}

	@Test
	@DisplayName("Debería obtener una localidad por ID existente")
	void deberiaObtenerLocalidadPorIdExistente() {
		// Arrange
		LocalidadEntity localidadEntity = LocalidadEntity.builder().id(LOCALIDAD_ID).denominacion(LOCALIDAD_NOMBRE)
				.build();

		ComunidadAutonoma comunidad = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE, null,
				null);
		Provincia provincia = new Provincia(PROVINCIA_ID, PROVINCIA_NOMBRE, comunidad);
		Localidad localidadDominio = new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE, provincia);

		when(jpaLocalidadRepository.findById(LOCALIDAD_ID)).thenReturn(Optional.of(localidadEntity));
		when(localidadMapper.toDomain(localidadEntity)).thenReturn(localidadDominio);

		// Act
		Optional<Localidad> resultado = adapter.getLocalidadPorId(LOCALIDAD_ID);

		// Assert
		assertTrue(resultado.isPresent());
		assertEquals(localidadDominio, resultado.get());
		verify(jpaLocalidadRepository, times(1)).findById(LOCALIDAD_ID);
		verify(localidadMapper, times(1)).toDomain(localidadEntity);
	}

	@Test
	@DisplayName("Debería retornar Optional.empty() cuando la localidad no existe")
	void deberiaRetornarEmptyCuandoLocalidadNoExiste() {
		// Arrange
		String localidadId = "INVALIDO";
		when(jpaLocalidadRepository.findById(localidadId)).thenReturn(Optional.empty());

		// Act
		Optional<Localidad> resultado = adapter.getLocalidadPorId(localidadId);

		// Assert
		assertFalse(resultado.isPresent());
		verify(jpaLocalidadRepository, times(1)).findById(localidadId);
		verify(localidadMapper, never()).toDomain(any());
	}

	@Test
	@DisplayName("Debería obtener localidades paginadas sin filtro")
	void deberiaObtenerLocalidadesPaginadasSinFiltro() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.by(Sort.Direction.ASC, "denominacion"));

		LocalidadEntity entity1 = LocalidadEntity.builder().id(LOCALIDAD_ID).denominacion(LOCALIDAD_NOMBRE).build();
		LocalidadEntity entity2 = LocalidadEntity.builder().id(LOCALIDAD_ID_2).denominacion(LOCALIDAD_NOMBRE_2).build();

		List<LocalidadEntity> entities = List.of(entity1, entity2);
		Page<LocalidadEntity> pageResult = new PageImpl<>(entities, pageable, entities.size());

		ComunidadAutonoma comunidad1 = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE, null,
				null);
		ComunidadAutonoma comunidad2 = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID_2, COMUNIDAD_AUTONOMA_NOMBRE_2, null,
				null);
		Provincia provincia1 = new Provincia(PROVINCIA_ID, PROVINCIA_NOMBRE, comunidad1);
		Provincia provincia2 = new Provincia(PROVINCIA_ID_2, PROVINCIA_NOMBRE_2, comunidad2);
		Localidad localidad1 = new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE, provincia1);
		Localidad localidad2 = new Localidad(LOCALIDAD_ID_2, LOCALIDAD_NOMBRE_2, provincia2);

		when(jpaLocalidadRepository.findAll(any(Pageable.class))).thenReturn(pageResult);
		when(localidadMapper.toDomain(entity1)).thenReturn(localidad1);
		when(localidadMapper.toDomain(entity2)).thenReturn(localidad2);

		// Act
		Pagina<Localidad> resultado = adapter.getLocalidadesPaginadas(paginacion, QUERY);

		// Assert
		assertNotNull(resultado);
		assertEquals(2, resultado.getItems().size());
		assertEquals(2, resultado.getTotalItems());
		assertEquals(PAGE, resultado.getNumeroPagina());
		assertEquals(SIZE, resultado.getTamanoPagina());
		assertTrue(resultado.getItems().contains(localidad1));
		assertTrue(resultado.getItems().contains(localidad2));
		verify(jpaLocalidadRepository, times(1)).findAll(any(Pageable.class));
	}

	@Test
	@DisplayName("Debería obtener localidades paginadas con filtro de búsqueda")
	void deberiaObtenerLocalidadesPaginadasConFiltro() {
		// Arrange
		String query = "Madrid";
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.by(Sort.Direction.ASC, "denominacion"));

		LocalidadEntity entity1 = LocalidadEntity.builder().id(LOCALIDAD_ID).denominacion(LOCALIDAD_NOMBRE).build();

		List<LocalidadEntity> entities = List.of(entity1);
		Page<LocalidadEntity> pageResult = new PageImpl<>(entities, pageable, entities.size());

		ComunidadAutonoma comunidad = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE, null,
				null);
		Provincia provincia = new Provincia(PROVINCIA_ID, PROVINCIA_NOMBRE, comunidad);
		Localidad localidad = new Localidad(LOCALIDAD_ID, LOCALIDAD_NOMBRE, provincia);

		when(jpaLocalidadRepository.findByDenominacionContaining(query, pageable)).thenReturn(pageResult);
		when(localidadMapper.toDomain(entity1)).thenReturn(localidad);

		// Act
		Pagina<Localidad> resultado = adapter.getLocalidadesPaginadas(paginacion, query);

		// Assert
		assertNotNull(resultado);
		assertEquals(1, resultado.getItems().size());
		assertEquals(1, resultado.getTotalItems());
		assertTrue(resultado.getItems().contains(localidad));
		verify(jpaLocalidadRepository, times(1)).findByDenominacionContaining(query, pageable);
	}

	@Test
	@DisplayName("Debería retornar página vacía cuando no hay localidades")
	void deberiaRetornarPaginaVaciaCuandoNoHayLocalidades() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.by(Sort.Direction.ASC, "denominacion"));
		Page<LocalidadEntity> pageResult = new PageImpl<>(List.of(), pageable, 0);

		when(jpaLocalidadRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

		// Act
		Pagina<Localidad> resultado = adapter.getLocalidadesPaginadas(paginacion, QUERY);

		// Assert
		assertNotNull(resultado);
		assertTrue(resultado.getItems().isEmpty());
		assertEquals(0, resultado.getTotalItems());
		verify(jpaLocalidadRepository, times(1)).findAll(any(Pageable.class));
	}
}

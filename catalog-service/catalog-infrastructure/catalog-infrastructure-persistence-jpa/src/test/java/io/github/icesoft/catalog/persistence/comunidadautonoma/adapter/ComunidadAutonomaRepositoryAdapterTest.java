package io.github.icesoft.catalog.persistence.comunidadautonoma.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.comunidadautonoma.model.ComunidadAutonoma;
import io.github.icesoft.catalog.domain.pais.port.repository.PaisRepositoryPort;
import io.github.icesoft.catalog.persistence.comunidadautonoma.entities.ComunidadAutonomaEntity;
import io.github.icesoft.catalog.persistence.comunidadautonoma.mapper.ComunidadAutonomaEntityMapper;
import io.github.icesoft.catalog.persistence.comunidadautonoma.repository.JpaComunidadAutonomaRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Tests unitarios para ComunidadAutonomaRepositoryAdapter
 */
@DisplayName("Tests de ComunidadAutonomaRepositoryAdapter")
@ExtendWith(MockitoExtension.class)
class ComunidadAutonomaRepositoryAdapterTest {

	private static final String COMUNIDAD_AUTONOMA_ID = "13";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE = "Comunidad de Madrid";
	private static final String COMUNIDAD_AUTONOMA_ID_2 = "09";
	private static final String COMUNIDAD_AUTONOMA_NOMBRE_2 = "Cataluña";
	private static final int PAGE = 0;
	private static final int SIZE = 10;

	@Mock
	private JpaComunidadAutonomaRepository jpaComunidadAutonomaRepository;

	@Mock
	private ComunidadAutonomaEntityMapper comunidadAutonomaMapper;

	@Mock
	private PaisRepositoryPort paisRepository;

	private ComunidadAutonomaRepositoryAdapter adapter;

	@BeforeEach
	void setUp() {
		adapter = new ComunidadAutonomaRepositoryAdapter(jpaComunidadAutonomaRepository, comunidadAutonomaMapper);
	}

	@Test
	@DisplayName("Debería obtener una comunidad autónoma por ID existente")
	void deberiaObtenerComunidadAutonomaPorIdExistente() {
		// Arrange
		ComunidadAutonomaEntity comunidadEntity = ComunidadAutonomaEntity.builder().id(COMUNIDAD_AUTONOMA_ID)
				.denominacion(COMUNIDAD_AUTONOMA_NOMBRE).build();
		ComunidadAutonoma comunidadDominio = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE,
				null, null);

		when(jpaComunidadAutonomaRepository.findById(COMUNIDAD_AUTONOMA_ID)).thenReturn(Optional.of(comunidadEntity));
		when(comunidadAutonomaMapper.toDomain(comunidadEntity)).thenReturn(comunidadDominio);

		// Act
		Optional<ComunidadAutonoma> resultado = adapter.getComunidadPorId(COMUNIDAD_AUTONOMA_ID);

		// Assert
		assertTrue(resultado.isPresent());
		assertEquals(comunidadDominio, resultado.get());
		verify(jpaComunidadAutonomaRepository, times(1)).findById(COMUNIDAD_AUTONOMA_ID);
		verify(comunidadAutonomaMapper, times(1)).toDomain(comunidadEntity);
	}

	@Test
	@DisplayName("Debería retornar Optional.empty() cuando la comunidad autónoma no existe")
	void deberiaRetornarEmptyCuandoComunidadNoExiste() {
		// Arrange
		String comunidadId = "INVALIDO";
		when(jpaComunidadAutonomaRepository.findById(comunidadId)).thenReturn(Optional.empty());

		// Act
		Optional<ComunidadAutonoma> resultado = adapter.getComunidadPorId(comunidadId);

		// Assert
		assertFalse(resultado.isPresent());
		verify(jpaComunidadAutonomaRepository, times(1)).findById(comunidadId);
		verify(comunidadAutonomaMapper, never()).toDomain(any());
	}

	@Test
	@DisplayName("Debería obtener comunidades autónomas paginadas")
	void deberiaObtenerComunidadesAutonomasPaginadas() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.by(Sort.Direction.ASC, "denominacion"));

		ComunidadAutonomaEntity entity1 = ComunidadAutonomaEntity.builder().id(COMUNIDAD_AUTONOMA_ID)
				.denominacion(COMUNIDAD_AUTONOMA_NOMBRE).build();
		ComunidadAutonomaEntity entity2 = ComunidadAutonomaEntity.builder().id(COMUNIDAD_AUTONOMA_ID_2)
				.denominacion(COMUNIDAD_AUTONOMA_NOMBRE_2).build();

		List<ComunidadAutonomaEntity> entities = List.of(entity1, entity2);
		Page<ComunidadAutonomaEntity> pageResult = new PageImpl<>(entities, pageable, entities.size());

		ComunidadAutonoma comunidad1 = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID, COMUNIDAD_AUTONOMA_NOMBRE, null,
				null);
		ComunidadAutonoma comunidad2 = new ComunidadAutonoma(COMUNIDAD_AUTONOMA_ID_2, COMUNIDAD_AUTONOMA_NOMBRE_2, null,
				null);

		when(jpaComunidadAutonomaRepository.findAll(any(Pageable.class))).thenReturn(pageResult);
		when(comunidadAutonomaMapper.toDomain(entity1)).thenReturn(comunidad1);
		when(comunidadAutonomaMapper.toDomain(entity2)).thenReturn(comunidad2);

		// Act
		Pagina<ComunidadAutonoma> resultado = adapter.getComunidadesPaginadas(paginacion);

		// Assert
		assertNotNull(resultado);
		assertEquals(2, resultado.getItems().size());
		assertEquals(2, resultado.getTotalItems());
		assertEquals(PAGE, resultado.getNumeroPagina());
		assertEquals(SIZE, resultado.getTamanoPagina());
		assertTrue(resultado.getItems().contains(comunidad1));
		assertTrue(resultado.getItems().contains(comunidad2));
		verify(jpaComunidadAutonomaRepository, times(1)).findAll(any(Pageable.class));
	}

	@Test
	@DisplayName("Debería retornar página vacía cuando no hay comunidades autónomas")
	void deberiaRetornarPaginaVaciaCuandoNoHayComunidades() {
		// Arrange
		Paginacion paginacion = new Paginacion(PAGE, SIZE);
		Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.by(Sort.Direction.ASC, "denominacion"));
		Page<ComunidadAutonomaEntity> pageResult = new PageImpl<>(List.of(), pageable, 0);

		when(jpaComunidadAutonomaRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

		// Act
		Pagina<ComunidadAutonoma> resultado = adapter.getComunidadesPaginadas(paginacion);

		// Assert
		assertNotNull(resultado);
		assertTrue(resultado.getItems().isEmpty());
		assertEquals(0, resultado.getTotalItems());
		verify(jpaComunidadAutonomaRepository, times(1)).findAll(any(Pageable.class));
	}
}

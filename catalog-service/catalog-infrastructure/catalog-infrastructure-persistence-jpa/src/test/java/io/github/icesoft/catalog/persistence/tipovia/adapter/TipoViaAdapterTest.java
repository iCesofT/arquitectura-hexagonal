package io.github.icesoft.catalog.persistence.tipovia.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.github.icesoft.catalog.domain.common.model.Pagina;
import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.tipovia.model.TipoVia;
import io.github.icesoft.catalog.persistence.tipovia.entities.TipoViaEntity;
import io.github.icesoft.catalog.persistence.tipovia.mapper.TipoViaEntityMapper;
import io.github.icesoft.catalog.persistence.tipovia.repositories.JpaTipoViaRepository;
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
 * Tests unitarios para TipoViaAdapter
 */
@DisplayName("Tests de TipoViaAdapter")
@ExtendWith(MockitoExtension.class)
class TipoViaAdapterTest {

	private static final String TIPO_VIA_AVENIDA_ID = "18";
	private static final String TIPO_VIA_AVENIDA_CLAVE = "Avenida";
	private static final String TIPO_VIA_AVENIDA_DENOMINACION = "-avenida-avda-avda.-av.-";

	private static final String TIPO_VIA_CALLE_ID = "2";
	private static final String TIPO_VIA_CALLE_CLAVE = "Calle";
	private static final String TIPO_VIA_CALLE_DENOMINACION = "-calle-c/-";

	@Mock
	private JpaTipoViaRepository tipoViaRepository;

	@Mock
	private TipoViaEntityMapper tipoViaMapper;

	private TipoViaAdapter adapter;

	@BeforeEach
	void setUp() {
		adapter = new TipoViaAdapter(tipoViaRepository, tipoViaMapper);
	}

	@Test
	@DisplayName("Debería obtener un tipo de vía por ID existente")
	void deberiaObtenerTipoViaPorIdExistente() {
		// Arrange
		String tipoViaId = "CL";
		TipoViaEntity tipoViaEntity = TipoViaEntity.builder().id(TIPO_VIA_CALLE_ID).clave(TIPO_VIA_CALLE_CLAVE)
				.denominacion(TIPO_VIA_CALLE_DENOMINACION).build();
		TipoVia tipoViaDominio = new TipoVia(TIPO_VIA_CALLE_ID, TIPO_VIA_CALLE_DENOMINACION, TIPO_VIA_CALLE_CLAVE);

		when(tipoViaRepository.findById(tipoViaId)).thenReturn(Optional.of(tipoViaEntity));
		when(tipoViaMapper.toDomain(tipoViaEntity)).thenReturn(tipoViaDominio);

		// Act
		Optional<TipoVia> resultado = adapter.getTipoViaPorId(tipoViaId);

		// Assert
		assertTrue(resultado.isPresent());
		assertEquals(tipoViaDominio, resultado.get());
		verify(tipoViaRepository, times(1)).findById(tipoViaId);
		verify(tipoViaMapper, times(1)).toDomain(tipoViaEntity);
	}

	@Test
	@DisplayName("Debería retornar Optional.empty() cuando el tipo de vía no existe")
	void deberiaRetornarEmptyCuandoTipoViaNoExiste() {
		// Arrange
		String tipoViaId = "INVALIDO";
		when(tipoViaRepository.findById(tipoViaId)).thenReturn(Optional.empty());

		// Act
		Optional<TipoVia> resultado = adapter.getTipoViaPorId(tipoViaId);

		// Assert
		assertFalse(resultado.isPresent());
		verify(tipoViaRepository, times(1)).findById(tipoViaId);
		verify(tipoViaMapper, never()).toDomain(any());
	}

	@Test
	@DisplayName("Debería consultar el repositorio JPA correctamente")
	void deberiaConsultarRepositorioJpaCorrectamente() {
		// Arrange
		String tipoViaId = "AV";
		when(tipoViaRepository.findById(tipoViaId)).thenReturn(Optional.empty());

		// Act
		adapter.getTipoViaPorId(tipoViaId);

		// Assert
		verify(tipoViaRepository).findById(tipoViaId);
	}

	@Test
	@DisplayName("Debería obtener tipos de vía paginados")
	void deberiaObtenerTiposViaPaginados() {
		// Arrange
		Paginacion paginacion = Paginacion.of(0, 10);
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "denominacion"));

		TipoViaEntity entity1 = TipoViaEntity.builder().id(TIPO_VIA_AVENIDA_ID).clave(TIPO_VIA_AVENIDA_CLAVE)
				.denominacion(TIPO_VIA_AVENIDA_DENOMINACION).build();
		TipoViaEntity entity2 = TipoViaEntity.builder().id(TIPO_VIA_CALLE_ID).clave(TIPO_VIA_CALLE_CLAVE)
				.denominacion(TIPO_VIA_CALLE_DENOMINACION).build();

		List<TipoViaEntity> entities = List.of(entity1, entity2);
		Page<TipoViaEntity> pageResult = new PageImpl<>(entities, pageable, entities.size());

		TipoVia tipoVia1 = new TipoVia(TIPO_VIA_AVENIDA_ID, TIPO_VIA_AVENIDA_CLAVE, TIPO_VIA_AVENIDA_DENOMINACION);
		TipoVia tipoVia2 = new TipoVia(TIPO_VIA_CALLE_ID, TIPO_VIA_CALLE_CLAVE, TIPO_VIA_CALLE_DENOMINACION);

		when(tipoViaRepository.findAll(any(Pageable.class))).thenReturn(pageResult);
		when(tipoViaMapper.toDomain(entity1)).thenReturn(tipoVia1);
		when(tipoViaMapper.toDomain(entity2)).thenReturn(tipoVia2);

		// Act
		Pagina<TipoVia> resultado = adapter.getTiposViaPaginados(paginacion);

		// Assert
		assertNotNull(resultado);
		assertEquals(2, resultado.getItems().size());
		assertEquals(2, resultado.getTotalItems());
		assertEquals(0, resultado.getNumeroPagina());
		assertEquals(10, resultado.getTamanoPagina());
		assertTrue(resultado.getItems().contains(tipoVia1));
		assertTrue(resultado.getItems().contains(tipoVia2));

		verify(tipoViaRepository, times(1)).findAll(any(Pageable.class));
		verify(tipoViaMapper, times(2)).toDomain(any(TipoViaEntity.class));
	}

	@Test
	@DisplayName("Debería obtener una página vacía cuando no hay tipos de vía")
	void deberiaObtenerPaginaVaciaCuandoNoHayTiposVia() {
		// Arrange
		Paginacion paginacion = Paginacion.of(0, 10);
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "denominacion"));

		Page<TipoViaEntity> pageResult = new PageImpl<>(List.of(), pageable, 0);

		when(tipoViaRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

		// Act
		Pagina<TipoVia> resultado = adapter.getTiposViaPaginados(paginacion);

		// Assert
		assertNotNull(resultado);
		assertTrue(resultado.getItems().isEmpty());
		assertEquals(0, resultado.getTotalItems());
		assertEquals(0, resultado.getNumeroPagina());
		assertEquals(10, resultado.getTamanoPagina());

		verify(tipoViaRepository, times(1)).findAll(any(Pageable.class));
		verify(tipoViaMapper, never()).toDomain(any());
	}

}
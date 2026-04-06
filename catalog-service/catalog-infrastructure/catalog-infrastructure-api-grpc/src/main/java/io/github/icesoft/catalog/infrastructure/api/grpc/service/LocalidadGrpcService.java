package io.github.icesoft.catalog.infrastructure.api.grpc.service;

import io.github.icesoft.catalog.application.localidad.query.GetLocalidadByIdQuery;
import io.github.icesoft.catalog.application.localidad.query.GetPaginatedLocalidadesQuery;
import io.github.icesoft.catalog.application.mediator.Mediator;
import io.github.icesoft.catalog.infrastructure.api.common.util.PaginacionMapper;
import io.github.icesoft.catalog.infrastructure.api.grpc.GetLocalidadByIdRequest;
import io.github.icesoft.catalog.infrastructure.api.grpc.ListLocalidadesRequest;
import io.github.icesoft.catalog.infrastructure.api.grpc.LocalidadDetalle;
import io.github.icesoft.catalog.infrastructure.api.grpc.LocalidadPageResult;
import io.github.icesoft.catalog.infrastructure.api.grpc.LocalidadServiceGrpc;
import io.github.icesoft.catalog.infrastructure.api.grpc.mapper.LocalidadGrpcMapper;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio gRPC para Localidad
 */
@GrpcService
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LocalidadGrpcService extends LocalidadServiceGrpc.LocalidadServiceImplBase {

	private final Mediator mediator;
	private final LocalidadGrpcMapper mapper;
	private final PaginacionMapper paginacionMapper;

	@Override
	public void listLocalidades(ListLocalidadesRequest request, StreamObserver<LocalidadPageResult> responseObserver) {
		log.info("Getting localidades list for language: {}, page: {}, size: {}", request.getLang(), request.getPage(),
				request.getSize());
		try {
			var paginacion = paginacionMapper.toPaginacion(request.getPage(), request.getSize());

			var query = new GetPaginatedLocalidadesQuery(paginacion, request.getQ().isEmpty() ? null : request.getQ());

			var resultado = mediator.send(query);
			var pageResult = mapper.toPageResult(resultado);

			responseObserver.onNext(pageResult);
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}

	@Override
	public void getLocalidadById(GetLocalidadByIdRequest request, StreamObserver<LocalidadDetalle> responseObserver) {
		log.info("Getting localidad detail for id: {}", request.getId());
		try {
			var query = new GetLocalidadByIdQuery(request.getId());
			var localidad = mediator.send(query);
			var detalle = mapper.toDetalle(localidad);

			responseObserver.onNext(detalle);
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}
}
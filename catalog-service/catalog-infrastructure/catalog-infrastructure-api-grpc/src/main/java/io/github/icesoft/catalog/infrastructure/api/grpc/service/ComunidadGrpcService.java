package io.github.icesoft.catalog.infrastructure.api.grpc.service;

import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.comunidadautonoma.usecase.GetComunidadAutonomaByIdUseCase;
import io.github.icesoft.catalog.domain.comunidadautonoma.usecase.GetPaginatedComunidadesAutonomasUseCase;
import io.github.icesoft.catalog.domain.provincia.usecase.GetProvinciasByComunidadIdUseCase;
import io.github.icesoft.catalog.infrastructure.api.grpc.ComunidadDetalle;
import io.github.icesoft.catalog.infrastructure.api.grpc.ComunidadServiceGrpc;
import io.github.icesoft.catalog.infrastructure.api.grpc.GetComunidadByIdRequest;
import io.github.icesoft.catalog.infrastructure.api.grpc.ListComunidadesRequest;
import io.github.icesoft.catalog.infrastructure.api.grpc.ListComunidadesResponse;
import io.github.icesoft.catalog.infrastructure.api.grpc.mapper.ComunidadGrpcMapper;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio gRPC para ComunidadAutonoma
 */
@GrpcService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComunidadGrpcService extends ComunidadServiceGrpc.ComunidadServiceImplBase {

	private final GetPaginatedComunidadesAutonomasUseCase getPaginatedComunidadesUseCase;
	private final GetComunidadAutonomaByIdUseCase getComunidadByIdUseCase;
	private final GetProvinciasByComunidadIdUseCase getProvinciasByComunidadIdUseCase;
	private final ComunidadGrpcMapper mapper;

	@Override
	public void listComunidades(ListComunidadesRequest request,
			StreamObserver<ListComunidadesResponse> responseObserver) {
		try {
			var paginacion = new Paginacion(0, Integer.MAX_VALUE);
			var resultado = getPaginatedComunidadesUseCase.execute(paginacion);

			var responseBuilder = ListComunidadesResponse.newBuilder();
			resultado.items().forEach(comunidad -> responseBuilder.addItems(mapper.toResumen(comunidad)));

			responseObserver.onNext(responseBuilder.build());
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}

	@Override
	public void getComunidadById(GetComunidadByIdRequest request, StreamObserver<ComunidadDetalle> responseObserver) {
		try {
			var comunidad = getComunidadByIdUseCase.execute(request.getId());
			var provincias = getProvinciasByComunidadIdUseCase.execute(request.getId());

			responseObserver.onNext(mapper.toDetalle(comunidad, provincias));
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}
}

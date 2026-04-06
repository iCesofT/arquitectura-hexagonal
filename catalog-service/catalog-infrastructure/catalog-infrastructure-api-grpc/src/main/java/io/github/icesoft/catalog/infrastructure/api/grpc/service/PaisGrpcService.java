package io.github.icesoft.catalog.infrastructure.api.grpc.service;

import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.pais.usecase.GetPaginatedPaisesUseCase;
import io.github.icesoft.catalog.domain.pais.usecase.GetPaisByIdUseCase;
import io.github.icesoft.catalog.infrastructure.api.grpc.GetPaisByIdRequest;
import io.github.icesoft.catalog.infrastructure.api.grpc.ListPaisesRequest;
import io.github.icesoft.catalog.infrastructure.api.grpc.ListPaisesResponse;
import io.github.icesoft.catalog.infrastructure.api.grpc.PaisDetalle;
import io.github.icesoft.catalog.infrastructure.api.grpc.PaisServiceGrpc;
import io.github.icesoft.catalog.infrastructure.api.grpc.mapper.PaisGrpcMapper;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio gRPC para País
 */
@GrpcService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaisGrpcService extends PaisServiceGrpc.PaisServiceImplBase {

	private final GetPaginatedPaisesUseCase getPaginatedPaisesUseCase;
	private final GetPaisByIdUseCase getPaisByIdUseCase;
	private final PaisGrpcMapper mapper;

	@Override
	public void listPaises(ListPaisesRequest request, StreamObserver<ListPaisesResponse> responseObserver) {
		try {
			// Usar paginación por defecto para listar todos
			var paginacion = new Paginacion(0, Integer.MAX_VALUE);
			var resultado = getPaginatedPaisesUseCase.execute(paginacion);

			var responseBuilder = ListPaisesResponse.newBuilder();
			resultado.items().forEach(pais -> responseBuilder.addItems(mapper.toResumen(pais)));

			responseObserver.onNext(responseBuilder.build());
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}

	@Override
	public void getPaisById(GetPaisByIdRequest request, StreamObserver<PaisDetalle> responseObserver) {
		try {
			var pais = getPaisByIdUseCase.execute(request.getId());
			responseObserver.onNext(mapper.toDetalle(pais));
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}
}
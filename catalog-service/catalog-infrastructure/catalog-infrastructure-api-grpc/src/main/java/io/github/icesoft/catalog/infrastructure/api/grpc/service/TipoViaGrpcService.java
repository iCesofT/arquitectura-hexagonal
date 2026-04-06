package io.github.icesoft.catalog.infrastructure.api.grpc.service;

import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.tipovia.usecase.GetPaginatedTiposViaUseCase;
import io.github.icesoft.catalog.domain.tipovia.usecase.GetTipoViaByIdUseCase;
import io.github.icesoft.catalog.infrastructure.api.grpc.GetTipoViaByIdRequest;
import io.github.icesoft.catalog.infrastructure.api.grpc.ListTiposViaRequest;
import io.github.icesoft.catalog.infrastructure.api.grpc.ListTiposViaResponse;
import io.github.icesoft.catalog.infrastructure.api.grpc.TipoViaDetalle;
import io.github.icesoft.catalog.infrastructure.api.grpc.TipoViaServiceGrpc;
import io.github.icesoft.catalog.infrastructure.api.grpc.mapper.TipoViaGrpcMapper;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio gRPC para TipoVia
 */
@GrpcService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TipoViaGrpcService extends TipoViaServiceGrpc.TipoViaServiceImplBase {

	private final GetPaginatedTiposViaUseCase getPaginatedTiposViaUseCase;
	private final GetTipoViaByIdUseCase getTipoViaByIdUseCase;
	private final TipoViaGrpcMapper mapper;

	@Override
	public void listTiposVia(ListTiposViaRequest request, StreamObserver<ListTiposViaResponse> responseObserver) {
		try {
			// Usar paginación por defecto para listar todos
			var paginacion = new Paginacion(0, Integer.MAX_VALUE);
			var resultado = getPaginatedTiposViaUseCase.execute(paginacion);

			var responseBuilder = ListTiposViaResponse.newBuilder();
			resultado.items().forEach(tipoVia -> responseBuilder.addItems(mapper.toResumen(tipoVia)));

			responseObserver.onNext(responseBuilder.build());
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}

	@Override
	public void getTipoViaById(GetTipoViaByIdRequest request, StreamObserver<TipoViaDetalle> responseObserver) {
		try {
			var tipoVia = getTipoViaByIdUseCase.execute(request.getId());
			responseObserver.onNext(mapper.toDetalle(tipoVia));
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}
}
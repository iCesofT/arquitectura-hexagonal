package io.github.icesoft.catalog.infrastructure.api.grpc.service;

import io.github.icesoft.catalog.domain.common.model.Paginacion;
import io.github.icesoft.catalog.domain.provincia.usecase.GetPaginatedProvinciasUseCase;
import io.github.icesoft.catalog.domain.provincia.usecase.GetProvinciaByIdUseCase;
import io.github.icesoft.catalog.infrastructure.api.grpc.GetProvinciaByIdRequest;
import io.github.icesoft.catalog.infrastructure.api.grpc.ListProvinciasRequest;
import io.github.icesoft.catalog.infrastructure.api.grpc.ListProvinciasResponse;
import io.github.icesoft.catalog.infrastructure.api.grpc.ProvinciaDetalle;
import io.github.icesoft.catalog.infrastructure.api.grpc.ProvinciaServiceGrpc;
import io.github.icesoft.catalog.infrastructure.api.grpc.mapper.ProvinciaGrpcMapper;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio gRPC para Provincia
 */
@GrpcService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProvinciaGrpcService extends ProvinciaServiceGrpc.ProvinciaServiceImplBase {

	private final GetPaginatedProvinciasUseCase getPaginatedProvinciasUseCase;
	private final GetProvinciaByIdUseCase getProvinciaByIdUseCase;
	private final ProvinciaGrpcMapper mapper;

	@Override
	public void listProvincias(ListProvinciasRequest request, StreamObserver<ListProvinciasResponse> responseObserver) {
		try {
			// Usar paginación por defecto para listar todas
			var paginacion = new Paginacion(0, Integer.MAX_VALUE);
			var resultado = getPaginatedProvinciasUseCase.execute(paginacion);

			var responseBuilder = ListProvinciasResponse.newBuilder();
			resultado.items().forEach(provincia -> responseBuilder.addItems(mapper.toResumen(provincia)));

			responseObserver.onNext(responseBuilder.build());
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}

	@Override
	public void getProvinciaById(GetProvinciaByIdRequest request, StreamObserver<ProvinciaDetalle> responseObserver) {
		try {
			var provincia = getProvinciaByIdUseCase.execute(request.getId());
			var detalle = mapper.toDetalle(provincia);

			responseObserver.onNext(detalle);
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}
}
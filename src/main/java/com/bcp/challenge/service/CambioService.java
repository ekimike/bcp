package com.bcp.challenge.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcp.challenge.entity.Cambio;
import com.bcp.challenge.exception.ErrorCode;
import com.bcp.challenge.repository.CambioRepository;
import com.bcp.challenge.servicedto.request.UpdateCambioMonedaRequest;
import com.bcp.challenge.servicedto.response.CambioResponse;

import rx.Completable;
import rx.Single;

@Component
public class CambioService {

	@Autowired
	CambioRepository cambioRepository;
	
	public Single<CambioResponse> getCambio(String origen, String destino, BigDecimal monto) {
		
		return findCambioInRepository(origen,destino,monto);
	}
	
	private Single<CambioResponse> findCambioInRepository(String origen, String destino, BigDecimal monto) {

		return Single.create(onSubscribe -> {
			Optional<Cambio> optionalCambio = cambioRepository.findByOrigenAndDestino(origen, destino);
			
			if(!optionalCambio.isPresent())
				onSubscribe.onError(new EntityNotFoundException());
			else {
				CambioResponse response = toCambioResponse(optionalCambio.get());
				response.setMontoCalculado(response.getTipoCambio().multiply(monto));
				response.setMonto(monto);
				onSubscribe.onSuccess(response);
			}
		});
	}

	public Completable updateCambioMoneda(UpdateCambioMonedaRequest updateCambioMoneda) {
		
		return updateCambioMonedaToRepository(updateCambioMoneda);
	}

	private Completable updateCambioMonedaToRepository(UpdateCambioMonedaRequest updateCambioMoneda) {
		
		return Completable.create(onSubscribe -> {
			Optional<Cambio> optionalCambio = cambioRepository.findById(updateCambioMoneda.getId());
			
			if(!optionalCambio.isPresent())
				onSubscribe.onError(new EntityNotFoundException());
			else {
				Cambio cambio = optionalCambio.get();
				cambio.setTipoCambio(updateCambioMoneda.getTipoCambio());
				cambioRepository.save(cambio);
				onSubscribe.onCompleted();
				
			}
		});
	}
	
	public Single<List<CambioResponse>> getAllCambios() {
		
		return getAllCambiosInRepository().map(this::toCambioResponseList);
	}
	
	private Single<List<Cambio>> getAllCambiosInRepository() {

		return Single.create(singleSubscriber -> {
			List<Cambio> cambios = cambioRepository.findAll();
			singleSubscriber.onSuccess(cambios);
		});
	}

	private List<CambioResponse>toCambioResponseList(List<Cambio> cambioList) {
		
		return cambioList
				.stream()
				.map(this::toCambioResponse)
				.collect(Collectors.toList());
	}
	
	private CambioResponse toCambioResponse(Cambio cambio) {
		
		CambioResponse cambioResponse = new CambioResponse();
		BeanUtils.copyProperties(cambio, cambioResponse);
		return cambioResponse;
	}
}

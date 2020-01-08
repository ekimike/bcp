package com.bcp.challenge.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcp.challenge.controllerdto.request.UpdateCambioMonedaControllerRequest;
import com.bcp.challenge.controllerdto.response.BaseControllerResponse;
import com.bcp.challenge.controllerdto.response.CambioControllerResponse;
import com.bcp.challenge.controllerdto.response.CambioControllerResponseDetail;
import com.bcp.challenge.service.CambioService;
import com.bcp.challenge.servicedto.request.UpdateCambioMonedaRequest;
import com.bcp.challenge.servicedto.response.CambioResponse;

import rx.Single;
import rx.schedulers.Schedulers;

@RestController
@RequestMapping("/cambio-moneda")
public class CambioMonedaController {

	@Autowired
	private CambioService cambioService;

	@GetMapping(
			value = "/de/{de}/a/{a}/monto/{monto}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Single<ResponseEntity<BaseControllerResponse<CambioControllerResponseDetail>>> getCambio(
			@PathVariable final String de, 
			@PathVariable final String a,
			@PathVariable final BigDecimal monto) {

		return cambioService.getCambio(de, a, monto)
				.subscribeOn(Schedulers.io())
				.map(cambioResponse -> 
				ResponseEntity.ok(BaseControllerResponse.successWithData(toCambioControllerResponseDetail(cambioResponse)))
				);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Single<ResponseEntity<BaseControllerResponse<List<CambioControllerResponse>>>> getCambios() {

		return cambioService.getAllCambios().subscribeOn(Schedulers.io()).map(cambioResponses -> ResponseEntity
				.ok(BaseControllerResponse.successWithData(toCambioControllerResponseList(cambioResponses))));

	}

	private List<CambioControllerResponse> toCambioControllerResponseList(List<CambioResponse> cambioResponses) {

		cambioResponses.stream().map(this::toCambioControllerResponse)
				.collect(Collectors.toList());

		return cambioResponses.stream().map(this::toCambioControllerResponse).collect(Collectors.toList());
	}

	private CambioControllerResponseDetail toCambioControllerResponseDetail(CambioResponse cambioResponse) {

		CambioControllerResponseDetail response = new CambioControllerResponseDetail();
		BeanUtils.copyProperties(cambioResponse, response);
		return response;
	}
	
	private CambioControllerResponse toCambioControllerResponse(CambioResponse cambioResponse) {

		CambioControllerResponse response = new CambioControllerResponse();
		BeanUtils.copyProperties(cambioResponse, response);
		return response;
	}

	@PutMapping(
			value = "/{id}",
			consumes = MediaType.APPLICATION_JSON_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Single<ResponseEntity<BaseControllerResponse>> updateTipoCambio(
			@PathVariable long id,
			@RequestBody UpdateCambioMonedaControllerRequest updateCambioMonedaController) {

		return cambioService.updateCambioMoneda(toUpdateCambioMoneda(id, updateCambioMonedaController))
				.subscribeOn(Schedulers.io()).toSingle(() -> ResponseEntity.ok(BaseControllerResponse.successUpdate()));

	}
	
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE) 
	public Single<ResponseEntity<BaseControllerResponse>> updateTipoCambioPost(@RequestBody UpdateCambioMonedaRequest request) {
		
		UpdateCambioMonedaControllerRequest updateCambioMonedaControllerRequest = new UpdateCambioMonedaControllerRequest();
		updateCambioMonedaControllerRequest.setTipoCambio(request.getTipoCambio());
	
		return cambioService.updateCambioMoneda(toUpdateCambioMoneda(request.getId(), updateCambioMonedaControllerRequest))
				.subscribeOn(Schedulers.io()).toSingle(() -> ResponseEntity.ok(BaseControllerResponse.successUpdate()));
	}
	
	

	private UpdateCambioMonedaRequest toUpdateCambioMoneda(long id,
			UpdateCambioMonedaControllerRequest updateCambioMonedaController) {

		UpdateCambioMonedaRequest updateCambioMonedaRequest = new UpdateCambioMonedaRequest();
		BeanUtils.copyProperties(updateCambioMonedaController, updateCambioMonedaRequest);
		updateCambioMonedaRequest.setId(id);

		return updateCambioMonedaRequest;
	}

}

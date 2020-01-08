package com.bcp.challenge.servicedto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCambioMonedaRequest {

	private long id;
	private BigDecimal tipoCambio;
	
}

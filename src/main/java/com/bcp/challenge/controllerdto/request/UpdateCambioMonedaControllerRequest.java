package com.bcp.challenge.controllerdto.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCambioMonedaControllerRequest {

	private BigDecimal tipoCambio;
}

package com.bcp.challenge.controllerdto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CambioControllerResponseDetail {

	private BigDecimal tipoCambio;
	private String origen;
	private String destino;
	private BigDecimal montoCalculado;
	private BigDecimal monto;
}

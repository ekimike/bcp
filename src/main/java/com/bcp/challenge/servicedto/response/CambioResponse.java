package com.bcp.challenge.servicedto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CambioResponse {

//	private long id;
	private BigDecimal tipoCambio; 
	private String origen;
	private String destino;
	private BigDecimal montoCalculado;
	private BigDecimal monto;
}

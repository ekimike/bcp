package com.bcp.challenge.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cambio implements Serializable {

	private static final long serialVersionUID = -1425883944509385302L;

	@Id
	@Column(name = "cambio_id")
	private long id;
	
	@Column(name = "cambio_factor")
	private BigDecimal tipoCambio;
	
	@Column(name = "moneda_origen")
	private String origen;
	
	@Column(name = "moneda_destino")
	private String destino;
	
	private int port;

	public Cambio(long id, BigDecimal tipoCambio, String origen, String destino) {
		super();
		this.id = id;
		this.tipoCambio = tipoCambio;
		this.origen = origen;
		this.destino = destino;
	}
	
	
}

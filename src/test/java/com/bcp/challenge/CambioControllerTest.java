package com.bcp.challenge;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bcp.challenge.entity.Cambio;
import com.bcp.challenge.repository.CambioRepository;

class CambioControllerTest {
	
	@Mock
	private CambioRepository cambioRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void addCambio_success() {
		
		when(cambioRepository.findById(1L))
			.thenReturn(Optional.of(new Cambio(1L, BigDecimal.valueOf(35.7), "USA", "PEN")));
		
	}

}

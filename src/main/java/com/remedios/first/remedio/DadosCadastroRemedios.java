package com.remedios.first.remedio;

import java.time.LocalDate;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroRemedios(
		
		@NotBlank
		String nome,
		
		@Enumerated
		Via via,
		
		@NotBlank
		String lote,
		
		int quantidade,
		
		@Future
		LocalDate validade,
		
		@Enumerated
		Laboratorio laboratorio) {
}

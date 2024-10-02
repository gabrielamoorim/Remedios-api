package com.remedios.first.remedio;

import java.time.LocalDate;

public record DadosListagemRemedios(Long id, String nome, Via via, String lote, Laboratorio laboratorio, LocalDate validade) {

    //Construtor para converter de Remedio para DadosListagemRemedios
    public DadosListagemRemedios(Remedio remedio) {
        this(remedio.getId(), remedio.getNome(), remedio.getVia(), remedio.getLote(), remedio.getLaboratorio(), remedio.getValidade());
    }
}

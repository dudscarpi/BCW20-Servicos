package com.soulcode.Servicos.Models;

public enum StatusUsuario {

    ATIVO("Ativo"),
    INATIVO("Inativo");

    private String situacao;

    StatusUsuario(String situacao) {
        this.situacao = situacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}

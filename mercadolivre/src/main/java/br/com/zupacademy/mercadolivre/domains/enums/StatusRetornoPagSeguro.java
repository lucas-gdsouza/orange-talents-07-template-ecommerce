package br.com.zupacademy.mercadolivre.domains.enums;

public enum StatusRetornoPagSeguro {
    SUCESSO, ERRO;

    public StatusTransacao normaliza() {
        if (this.equals(SUCESSO)) {
            return StatusTransacao.SUCESSO;
        }
        return StatusTransacao.ERRO;
    }
}

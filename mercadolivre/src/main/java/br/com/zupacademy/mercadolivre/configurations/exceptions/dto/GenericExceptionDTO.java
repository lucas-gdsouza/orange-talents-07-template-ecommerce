package br.com.zupacademy.mercadolivre.configurations.exceptions.dto;

public class GenericExceptionDTO {

    private String campo;
    private String mensagem;

    public GenericExceptionDTO(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
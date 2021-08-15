package br.com.zupacademy.mercadolivre.dto.responses;

import br.com.zupacademy.mercadolivre.domains.Compra;

public class CompraResponse {

    private Compra compra;
    private String controllerMappingPath;

    public CompraResponse(Compra compra, String controllerMappingPath) {
        this.compra = compra;
        this.controllerMappingPath = controllerMappingPath;
    }

    public String getFinalURI() {
        Long idGeradorDaCompra = this.compra.getId();
        String gatewayURI = this.compra.getGatewayPagamento().gerarUrlDeRetorno(idGeradorDaCompra);
        String enderecoFinal = controllerMappingPath.concat(gatewayURI);

        return enderecoFinal;
    }
}
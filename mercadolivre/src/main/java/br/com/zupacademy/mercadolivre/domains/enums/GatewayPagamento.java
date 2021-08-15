package br.com.zupacademy.mercadolivre.domains.enums;

public enum GatewayPagamento {

    PayPal {
        @Override
        public String gerarUrlDeRetorno(Long idGeradorDaCompra) {
            String inicioURI = "paypal.com?buyerId=";
            String redirecionamentoURI = "&redirectUrl={urlDeRetorno}";

            return inicioURI.concat(idGeradorDaCompra.toString()).concat(redirecionamentoURI);
        }
    },

    PagSeguro {
        @Override
        public String gerarUrlDeRetorno(Long idGeradorDaCompra) {
            String inicioURI = "pagseguro.com?returnId=";
            String redirecionamentoURI = "&redirectUrl={urlDeRetorno}";

            return inicioURI.concat(idGeradorDaCompra.toString()).concat(redirecionamentoURI);
        }
    };

    public abstract String gerarUrlDeRetorno(Long idGeradorDaCompra);
}
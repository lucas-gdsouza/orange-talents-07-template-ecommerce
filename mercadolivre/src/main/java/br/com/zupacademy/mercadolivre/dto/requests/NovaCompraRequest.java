package br.com.zupacademy.mercadolivre.dto.requests;

import br.com.zupacademy.mercadolivre.annotations.ExistsId;
import br.com.zupacademy.mercadolivre.domains.Compra;
import br.com.zupacademy.mercadolivre.domains.Produto;
import br.com.zupacademy.mercadolivre.domains.Usuario;
import br.com.zupacademy.mercadolivre.domains.enums.GatewayPagamento;
import br.com.zupacademy.mercadolivre.domains.enums.StatusCompra;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NovaCompraRequest {

    @ExistsId(domainClass = Produto.class, fieldName = "id")
    @NotNull
    @Positive
    private Long idProduto;

    @NotNull
    @Positive
    private Integer quantidadeDesejadaPeloComprador;

    @NotNull
    private GatewayPagamento gatewayPagamento;


    public NovaCompraRequest(@NotNull @Positive Long idProduto,
                             @NotNull @Positive Integer quantidadeDesejadaPeloComprador,
                             @NotNull GatewayPagamento gatewayPagamento) {
        this.idProduto = idProduto;
        this.quantidadeDesejadaPeloComprador = quantidadeDesejadaPeloComprador;
        this.gatewayPagamento = gatewayPagamento;

    }

    public @NotNull Produto avaliarEstoque(EntityManager manager) {
        Produto produtoSelecionado = manager.find(Produto.class, this.idProduto);

        if (!produtoSelecionado.reduzirQuantidadeEstocada(this.quantidadeDesejadaPeloComprador)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return produtoSelecionado;
    }

    public Compra toModel(Usuario comprador, Produto produto) {
        return new Compra(comprador, produto, this.gatewayPagamento,
                this.quantidadeDesejadaPeloComprador, StatusCompra.INICIADA);
    }
}
package br.com.zupacademy.mercadolivre.domains;

import br.com.zupacademy.mercadolivre.domains.enums.GatewayPagamento;
import br.com.zupacademy.mercadolivre.domains.enums.StatusCompra;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "Compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @Valid
    private Usuario comprador;

    @ManyToOne
    @NotNull
    @Valid
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @NotNull
    private GatewayPagamento gatewayPagamento;

    @NotNull
    @Min(1)
    @Positive
    private Integer quantidadeDesejadaPeloComprador;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusCompra statusCompra;

    @Deprecated
    public Compra() {
    }

    public Compra(@NotNull @Valid Usuario comprador, @NotNull @Valid Produto produto,
                  @NotNull GatewayPagamento gatewayPagamento,
                  @NotNull @Positive Integer quantidadeDesejadaPeloComprador, @NotNull StatusCompra statusCompra) {

        validarArgumentos(comprador, produto, gatewayPagamento, quantidadeDesejadaPeloComprador, statusCompra);
        this.comprador = comprador;
        this.produto = produto;
        this.gatewayPagamento = gatewayPagamento;
        this.quantidadeDesejadaPeloComprador = quantidadeDesejadaPeloComprador;
        this.statusCompra = statusCompra;
    }

    private void validarArgumentos(Usuario comprador, Produto produto, GatewayPagamento gatewayPagamento,
                                   Integer quantidadeDesejadaPeloComprador, StatusCompra statusCompra) {
        Assert.notNull(comprador, "Argumento 'comprador' precisa ser preenchido.");
        Assert.notNull(produto, "Argumento 'produto' precisa ser preenchido.");
        Assert.notNull(gatewayPagamento, "Argumento 'gatewayPagamento' precisa ser preenchido.");
        Assert.isTrue(quantidadeDesejadaPeloComprador > 0,
                "A quantidade requerida precisa ser maior que 0");
        Assert.notNull(statusCompra, "Argumento 'statusPagamento' precisa ser preenchido.");
    }

    public Long getId() {
        return id;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Produto getProduto() {
        return produto;
    }

    public GatewayPagamento getGatewayPagamento() {
        return gatewayPagamento;
    }

    public Integer getQuantidadeDesejadaPeloComprador() {
        return quantidadeDesejadaPeloComprador;
    }

    public StatusCompra getStatusCompra() {
        return statusCompra;
    }
}
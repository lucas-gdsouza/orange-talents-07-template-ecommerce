package br.com.zupacademy.mercadolivre.domains;

import br.com.zupacademy.mercadolivre.domains.enums.GatewayPagamento;
import br.com.zupacademy.mercadolivre.dto.requests.impl.RetornoGatewayPagamento;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Transacao> transacoes = new HashSet<>();

    @Deprecated
    public Compra() {
    }

    public Compra(@NotNull @Valid Usuario comprador, @NotNull @Valid Produto produto,
                  @NotNull GatewayPagamento gatewayPagamento,
                  @NotNull @Positive Integer quantidadeDesejadaPeloComprador) {

        validarArgumentos(comprador, produto, gatewayPagamento, quantidadeDesejadaPeloComprador);
        this.comprador = comprador;
        this.produto = produto;
        this.gatewayPagamento = gatewayPagamento;
        this.quantidadeDesejadaPeloComprador = quantidadeDesejadaPeloComprador;
    }

    private void validarArgumentos(Usuario comprador, Produto produto, GatewayPagamento gatewayPagamento,
                                   Integer quantidadeDesejadaPeloComprador) {
        Assert.notNull(comprador, "Argumento 'comprador' precisa ser preenchido.");
        Assert.notNull(produto, "Argumento 'produto' precisa ser preenchido.");
        Assert.notNull(gatewayPagamento, "Argumento 'gatewayPagamento' precisa ser preenchido.");
        Assert.isTrue(quantidadeDesejadaPeloComprador > 0,
                "A quantidade requerida precisa ser maior que 0");
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


    /*Transação*/

    public void adicionaTransacao(@Valid RetornoGatewayPagamento request) {
        Transacao novaTransacao = request.toTransacao(this);

        Assert.state(!this.transacoes.contains(novaTransacao),
                "Atenção! Já existe uma transação validada!"
                        + novaTransacao);
        Assert.state(transacoesConcluidasComSucesso().isEmpty(), "Atenção - Compra já concluída!");

        this.transacoes.add(novaTransacao);
    }

    private Set<Transacao> transacoesConcluidasComSucesso() {
        Set<Transacao> transacoesConcluidasComSucesso = this.transacoes.stream()
                .filter(Transacao::concluidaComSucesso)
                .collect(Collectors.toSet());

        Assert.isTrue(transacoesConcluidasComSucesso.size() <= 1, "O ID está em uso mais de uma vez.");

        return transacoesConcluidasComSucesso;
    }

    public boolean processadaComSucesso() {
        return !transacoesConcluidasComSucesso().isEmpty();
    }
}
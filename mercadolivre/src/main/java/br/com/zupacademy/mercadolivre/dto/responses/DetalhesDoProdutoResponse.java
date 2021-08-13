package br.com.zupacademy.mercadolivre.dto.responses;

import br.com.zupacademy.mercadolivre.domains.*;
import br.com.zupacademy.mercadolivre.domains.treatments.Opinioes;

import java.math.BigDecimal;
import java.util.*;

public class DetalhesDoProdutoResponse {

    private String nome;
    private BigDecimal valor;
    private Integer quantidadeDisponivel;
    private String descricao;
    private Set<DetalhesCaracteristicasResponse> caracteristicas;
    private Set<String> imagens;
    private SortedSet<String> perguntas;
    private Set<Map<String,String>> opinioes;
    private Double notaMedia;
    private Integer total;


    public DetalhesDoProdutoResponse(Produto produto) {
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.quantidadeDisponivel = produto.getQuantidadeDisponivel();
        this.descricao = produto.getDescricao();

        this.caracteristicas = produto.mapCaracteristicas(caracteristica ->
                new DetalhesCaracteristicasResponse(caracteristica));
        this.imagens = produto.mapImagens(imagem -> imagem.getLink());
        this.perguntas = produto.mapPerguntas(pergunta -> pergunta.getTitulo());

        Opinioes opinioes = produto.getOpinioes();
        this.opinioes = opinioes.mapeiaOpinioes(opiniao -> {
            return Map.of("titulo",opiniao.getTitulo(),"descricao",opiniao.getDescricao());
        });

        this.notaMedia = opinioes.media();
        this.total = opinioes.total();
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public String getDescricao() {
        return descricao;
    }

    public Set<DetalhesCaracteristicasResponse> getCaracteristicas() {
        return caracteristicas;
    }

    public Set<String> getImagens() {
        return imagens;
    }

    public SortedSet<String> getPerguntas() {
        return perguntas;
    }

    public Set<Map<String, String>> getOpinioes() {
        return opinioes;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }

    public Integer getTotal() {
        return total;
    }
}
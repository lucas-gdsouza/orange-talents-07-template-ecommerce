package br.com.zupacademy.mercadolivre.dto.requests;

import br.com.zupacademy.mercadolivre.annotations.ExistsId;
import br.com.zupacademy.mercadolivre.domains.Categoria;
import br.com.zupacademy.mercadolivre.domains.Produto;
import br.com.zupacademy.mercadolivre.domains.Usuario;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class CadastroProdutoRequest {

    @NotBlank
    private String nome;

    @NotNull
    @DecimalMin(value = "1.00")
    private BigDecimal valor;

    @NotNull
    @PositiveOrZero
    private Integer quantidadeDisponivel;

    @NotNull
    @Size(min = 3, message = "Houve a tentativa de adicionar uma característica que já existe.")
    private Set<CadastroCaracteristicaRequest> caracteristicas;

    @NotBlank
    @Size(max = 1000)
    private String descricao;

    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    @NotNull
    @Positive
    private Long idCategoria;

    public CadastroProdutoRequest(@NotBlank String nome, @NotNull @DecimalMin(value = "1.00") BigDecimal valor,
                                  @NotNull @PositiveOrZero Integer quantidadeDisponivel,
                                  @NotNull @Size(min = 3) Set<CadastroCaracteristicaRequest> caracteristicas,
                                  @NotBlank String descricao,
                                  @NotNull @Positive Long idCategoria) {
        this.nome = nome;
        this.valor = valor;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
    }

    public Set<CadastroCaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Set<CadastroCaracteristicaRequest> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Set<String> buscaCaracaracteristicasIguais() {
        HashSet<String> nomesIguais = new HashSet<>();
        HashSet<String> resultados = new HashSet<>();

        for (CadastroCaracteristicaRequest caracteristica : caracteristicas) {
            String nome = caracteristica.getNome();

            if (!nomesIguais.add(nome)) {
                resultados.add(nome);
            }
        }
        return resultados;
    }

    public @NotNull Produto toModel(EntityManager manager, Usuario usuario) {
        Categoria categoria = manager.find(Categoria.class, this.idCategoria);
        return new Produto(this.nome, this.valor, this.quantidadeDisponivel, usuario,
                this.caracteristicas, this.descricao, categoria);
    }
}
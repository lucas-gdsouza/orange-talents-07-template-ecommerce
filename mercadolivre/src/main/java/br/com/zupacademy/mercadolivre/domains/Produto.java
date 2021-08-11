package br.com.zupacademy.mercadolivre.domains;

import br.com.zupacademy.mercadolivre.dto.requests.CadastroCaracteristicaRequest;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "Produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @DecimalMin(value = "1.00")
    private BigDecimal valor;

    @PositiveOrZero
    private Integer quantidadeDisponivel;

    @NotNull
    @Valid
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @Valid
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    @Size(min = 3)
    private Set<CaracteristicaProduto> caracteristicas = new HashSet<>();

    @NotBlank
    @Size(max = 1000)
    private String descricao;

    @ManyToOne
    @NotNull
    @Valid
    private Categoria categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();

    private LocalDate dataCadastro = LocalDate.now();

    @Deprecated
    public Produto() {
    }

    public Produto(@NotBlank String nome, @NotNull @DecimalMin(value = "1.00") BigDecimal valor,
                   @PositiveOrZero Integer quantidadeDisponivel,
                   @NotNull @Valid Usuario usuario,
                   @NotNull @Valid Collection<CadastroCaracteristicaRequest> caracteristicas,
                   @NotBlank @Size(max = 1000) String descricao,
                   @NotNull @Valid Categoria categoria) {

        validarArgumentos(nome, valor, quantidadeDisponivel, usuario, caracteristicas, descricao, categoria);
        this.nome = nome;
        this.valor = valor;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.usuario = usuario;
        this.descricao = descricao;
        this.caracteristicas.addAll(caracteristicas.stream()
                .map(caracteristica -> caracteristica.toModel(this))
                .collect(Collectors.toSet()));
        this.categoria = categoria;
    }

    private void validarArgumentos(String nome, BigDecimal valor, Integer quantidadeDisponivel,
                                   Usuario usuario,
                                   Collection<CadastroCaracteristicaRequest> caracteristicas,
                                   String descricao, Categoria categoria) {

        Assert.hasText(nome, "O argumento 'nome' deve ser preenchido.");

        Assert.notNull(valor, "O argumento 'valor' não pode ser null");
        Assert.isTrue(valor.compareTo(BigDecimal.valueOf(1.00)) != -1,
                "O argumento 'valor' deve ser maior que 1.00");

        Assert.notNull(quantidadeDisponivel, "O argumento 'quantidadeDisponivel' não pode ser null");
        Assert.isTrue(quantidadeDisponivel > 0, "O argumento 'quantidadeDisponivel' " +
                "deve ser maior ou igual a zero");

        Assert.notNull(usuario, "O argumento 'usuario' não pode ser null");

        Assert.isTrue(caracteristicas.size() >= 3,
                "O produto precisa ter no mínimo 3 ou mais características");

        Assert.hasText(descricao, "O argumento 'descricao' deve ser preenchido.");

        Assert.notNull(categoria, "O argumento 'categoria' não pode ser null");
    }

    public void associaImagens(Set<String> links) {
        Set<ImagemProduto> imagens = links.stream()
                .map(link -> new ImagemProduto(this, link))
                .collect(Collectors.toSet());

        this.imagens.addAll(imagens);
    }

    public boolean verificacaoOwnership(Usuario possivelDono) {
        return this.usuario.equals(possivelDono);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Produto other = (Produto) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }
}
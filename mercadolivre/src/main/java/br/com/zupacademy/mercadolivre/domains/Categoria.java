package br.com.zupacademy.mercadolivre.domains;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @ManyToOne
    private Categoria categoriaMae;

    @Deprecated
    public Categoria(){}

    /**
     *
     * @param nome - Obrigatório
     */
    public Categoria(@NotBlank String nome) {
        validarArgumentos(nome);
        this.nome = nome;
    }

    /**
     *
     * @param categoriaMae - Opcional
     */
    public void setCategoriaMae(Categoria categoriaMae) {
        this.categoriaMae = categoriaMae;
    }

    private void validarArgumentos(String nome) {
        if (nome == null || nome.trim().equals("")) {
            throw new IllegalArgumentException("Argumento 'nome' não pode ser null ou vazio");
        }
    }
}
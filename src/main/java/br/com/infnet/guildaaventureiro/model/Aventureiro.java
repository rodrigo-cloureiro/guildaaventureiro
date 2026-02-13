package br.com.infnet.guildaaventureiro.model;

import br.com.infnet.guildaaventureiro.enums.Classe;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Aventureiro {
    @Setter
    private Long id;
    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;
    @NotNull(message = "A classe é obrigatória")
    private Classe classe;
    @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
    private int nivel;
    @NotNull
    private Boolean ativo;
    @Valid
    private Companheiro companheiro;

    public Aventureiro(String nome, Classe classe, int nivel) {
        this.id = 1L;
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.ativo = true;
    }
}

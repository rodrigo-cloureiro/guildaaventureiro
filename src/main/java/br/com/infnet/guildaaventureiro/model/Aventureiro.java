package br.com.infnet.guildaaventureiro.model;

import br.com.infnet.guildaaventureiro.enums.Classe;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
public class Aventureiro {
    private UUID id;
    @NotNull(message = "O nome não pode ser vazio")
    private String nome;
    @NotNull
    private Classe classe;
    @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
    private int nivel;
    @NotNull
    private Boolean ativo;
    private Companheiro companheiro;
}

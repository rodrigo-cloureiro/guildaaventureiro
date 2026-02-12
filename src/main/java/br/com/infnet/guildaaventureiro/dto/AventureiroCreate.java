package br.com.infnet.guildaaventureiro.dto;

import br.com.infnet.guildaaventureiro.enums.Classe;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AventureiroCreate(
        @NotBlank(message = "O nome não pode ser vazio")
        String nome,
        @NotNull(message = "A classe é obrigatória")
        Classe classe,
        @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
        int nivel
) {
}

package br.com.infnet.guildaaventureiro.dto;

import br.com.infnet.guildaaventureiro.enums.Classe;
import jakarta.validation.constraints.Min;

public record AventureiroUpdate(
        String nome,
        Classe classe,
        @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
        Integer nivel
) {
}

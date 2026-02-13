package br.com.infnet.guildaaventureiro.dto;

import br.com.infnet.guildaaventureiro.enums.Classe;
import br.com.infnet.guildaaventureiro.model.Aventureiro;

public record AventureiroResponse(
        Long id,
        String nome,
        Classe classe,
        int nivel,
        boolean ativo
) {
    public AventureiroResponse(Aventureiro aventureiro) {
        this(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo()
        );
    }
}

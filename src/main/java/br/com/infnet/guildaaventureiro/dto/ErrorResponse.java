package br.com.infnet.guildaaventureiro.dto;

import java.util.List;

public record ErrorResponse(
        String mensagem,
        List<String> detalhes
) {
}

package br.com.infnet.guildaaventureiro.service;

import br.com.infnet.guildaaventureiro.model.Aventureiro;
import br.com.infnet.guildaaventureiro.repository.AventureiroRepositoryFake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AventureiroService {
    private final AventureiroRepositoryFake aventureiroRepository;

    public Aventureiro criar(Aventureiro aventureiro) {
        return aventureiroRepository.save(aventureiro);
    }
}

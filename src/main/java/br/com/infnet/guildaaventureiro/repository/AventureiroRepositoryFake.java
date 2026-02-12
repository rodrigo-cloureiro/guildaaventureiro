package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.model.Aventureiro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AventureiroRepositoryFake {
    private final List<Aventureiro> banco = new ArrayList<>();
}

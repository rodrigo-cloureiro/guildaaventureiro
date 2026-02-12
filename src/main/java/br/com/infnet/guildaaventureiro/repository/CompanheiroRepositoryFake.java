package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.model.Companheiro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanheiroRepositoryFake {
    private final List<Companheiro> banco = new ArrayList<>();
}

package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.enums.Classe;
import br.com.infnet.guildaaventureiro.model.Aventureiro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Repository
public class AventureiroRepositoryFake {
    private final List<Aventureiro> banco = new ArrayList<>();

    public AventureiroRepositoryFake() {
        inicializar();
    }

    public Aventureiro save(Aventureiro aventureiro) {
        banco.add(aventureiro);
        return aventureiro;
    }

    private void inicializar() {
        Random random = new Random();

        List<String> nomesPrefixos = Arrays.asList("Ragnar", "Elara", "Thrain", "Lyra",
                "Kael", "Cedric", "Morganna", "Valerius");
        List<String> nomesSufixos = Arrays.asList("o Bravo", "de Prata", "das Sombras",
                "o Sábio", "de Ferro", "Luz do Sol");
        List<String> classes = Arrays.stream(Classe.values()).map(Enum::name).toList();

        for (int i = 0; i < 100; i++) {
            String nome = nomesPrefixos.get(random.nextInt(nomesPrefixos.size()));
            String sufixo = nomesSufixos.get(random.nextInt(nomesSufixos.size()));
            String classe = classes.get(random.nextInt(classes.size()));
            int nivel = random.nextInt(100) + 1;

            Aventureiro aventureiro = new Aventureiro(
                    String.format("%s %s", nome, sufixo),
                    Classe.valueOf(classe),
                    nivel
            );
            banco.add(aventureiro);
        }
    }
}

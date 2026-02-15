package br.com.infnet.guildaaventureiro.service;

import br.com.infnet.guildaaventureiro.dto.AventureiroFiltroRequest;
import br.com.infnet.guildaaventureiro.dto.AventureiroUpdate;
import br.com.infnet.guildaaventureiro.dto.PagedResponse;
import br.com.infnet.guildaaventureiro.exception.EntidadeNaoLocalizadaException;
import br.com.infnet.guildaaventureiro.model.Aventureiro;
import br.com.infnet.guildaaventureiro.model.Companheiro;
import br.com.infnet.guildaaventureiro.repository.AventureiroRepositoryFake;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class AventureiroService {
    private final AventureiroRepositoryFake aventureiroRepository;

    public PagedResponse<Aventureiro> listar(
            @Valid AventureiroFiltroRequest filtro,
            int page,
            int size
    ) {
        return aventureiroRepository.findWithFilter(
                filtro,
                Comparator.comparing(Aventureiro::getId),
                page,
                size
        );
    }

    public Aventureiro buscarPorId(Long id) {
        return aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoLocalizadaException("Aventureiro não localizado"));
    }

    public Aventureiro atualizar(Long id, AventureiroUpdate update) {
        Aventureiro aventureiro = buscarPorId(id);

        if (update.nome() != null)
            aventureiro.alterarNome(update.nome());

        if (update.classe() != null)
            aventureiro.alterarClasse(update.classe());

        if (update.nivel() != null)
            aventureiro.alterarNivel(update.nivel());

        return aventureiroRepository.save(aventureiro);
    }

    public void encerrarVinculo(Long id) {
        Aventureiro aventureiro = buscarPorId(id);
        aventureiro.encerrarVinculo();
    }

    public void recrutarNovamente(Long id) {
        Aventureiro aventureiro = buscarPorId(id);
        aventureiro.recrutar();
    }

    public Aventureiro definirCompanheiro(Long id, Companheiro companheiro) {
        Aventureiro aventureiro = buscarPorId(id);
        aventureiro.definirCompanheiro(companheiro);
        return aventureiroRepository.save(aventureiro);
    }

    public Aventureiro criar(Aventureiro aventureiro) {
        return aventureiroRepository.save(aventureiro);
    }
}

package br.com.infnet.guildaaventureiro.service;

import br.com.infnet.guildaaventureiro.dto.AventureiroFiltroRequest;
import br.com.infnet.guildaaventureiro.dto.PagedResponse;
import br.com.infnet.guildaaventureiro.model.Aventureiro;
import br.com.infnet.guildaaventureiro.repository.AventureiroRepositoryFake;
import br.com.infnet.guildaaventureiro.util.Paginator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AventureiroService {
    private final AventureiroRepositoryFake aventureiroRepository;

    public PagedResponse<Aventureiro> listar(
            @Valid AventureiroFiltroRequest filtro,
            int page,
            int size
    ) {
        List<Aventureiro> filtrados = new ArrayList<>(
                aventureiroRepository.findWithFilter(filtro, Comparator.comparing(Aventureiro::getId))
        );
        return Paginator.paginate(filtrados, page, size);
    }

    public Aventureiro criar(Aventureiro aventureiro) {
        return aventureiroRepository.save(aventureiro);
    }
}

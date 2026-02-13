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

    /*
        AINDA FALTA:
           - Passar os filtros para o Repository:
            - public List<Aventureiro> buscarComFiltro(AventureiroFiltroRequest filtro)
            - aventureiroRepository.buscarComFiltro()
    */
    public PagedResponse<Aventureiro> listar(
            @Valid AventureiroFiltroRequest filtro,
            int page,
            int size
    ) {
        List<Aventureiro> filtrados = filtrar(filtro);
        ordenar(filtrados, Comparator.comparing(Aventureiro::getId));
        return Paginator.paginate(filtrados, page, size);
    }

    public Aventureiro criar(Aventureiro aventureiro) {
        return aventureiroRepository.save(aventureiro);
    }

    private List<Aventureiro> filtrar(AventureiroFiltroRequest filtro) {
        return new ArrayList<>(aventureiroRepository.findAll()
                .stream()
                .filter(a -> possuiFiltro(a.getClasse(), filtro.classe()))
                .filter(a -> possuiFiltro(a.getAtivo(), filtro.ativo()))
                .filter(a -> possuiFiltroNivelMinimo(a, filtro))
                .toList()
        );
    }

    private <T> boolean possuiFiltro(T objeto, T filtro) {
        return filtro == null || objeto.equals(filtro);
    }

    private boolean possuiFiltroNivelMinimo(Aventureiro aventureiro, AventureiroFiltroRequest filtro) {
        return filtro.nivelMinimo() == null || aventureiro.getNivel() >= filtro.nivelMinimo();
    }

    private void ordenar(List<Aventureiro> lista, Comparator<Aventureiro> comparator) {
        lista.sort(comparator);
    }
}

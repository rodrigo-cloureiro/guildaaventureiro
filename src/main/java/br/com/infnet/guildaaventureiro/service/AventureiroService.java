package br.com.infnet.guildaaventureiro.service;

import br.com.infnet.guildaaventureiro.dto.AventureiroFiltroRequest;
import br.com.infnet.guildaaventureiro.dto.PagedResponse;
import br.com.infnet.guildaaventureiro.model.Aventureiro;
import br.com.infnet.guildaaventureiro.repository.AventureiroRepositoryFake;
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
        return paginar(filtrados, page, size);
    }

    public Aventureiro criar(Aventureiro aventureiro) {
        return aventureiroRepository.save(aventureiro);
    }

    private List<Aventureiro> filtrar(AventureiroFiltroRequest filtro) {
        return new ArrayList<>(aventureiroRepository.findAll()
                .stream()
                .filter(a -> filtroPossuiClasse(a, filtro))
                .filter(a -> filtroPossuiAtivo(a, filtro))
                .filter(a -> filtroPossuiNivelMinimo(a, filtro))
                .toList()
        );
    }

    private boolean filtroPossuiClasse(Aventureiro aventureiro, AventureiroFiltroRequest filtro) {
        return filtro.classe() == null || aventureiro.getClasse().equals(filtro.classe());
    }

    private boolean filtroPossuiAtivo(Aventureiro aventureiro, AventureiroFiltroRequest filtro) {
        return filtro.ativo() == null || aventureiro.getAtivo().equals(filtro.ativo());
    }

    private boolean filtroPossuiNivelMinimo(Aventureiro aventureiro, AventureiroFiltroRequest filtro) {
        return filtro.nivelMinimo() == null || aventureiro.getNivel() >= filtro.nivelMinimo();
    }

    private void ordenar(List<Aventureiro> lista, Comparator<Aventureiro> comparator) {
        lista.sort(comparator);
    }

    private PagedResponse<Aventureiro> paginar(
            List<Aventureiro> lista,
            int page,
            int size
    ) {
        int total = lista.size();

        int fromIndex = calcularInicioDaLista(page, size, total);
        int toIndex = calcularFinalDaLista(fromIndex, size, total);

        return new PagedResponse<>(
                page,
                size,
                total,
                lista.subList(fromIndex, toIndex)
        );
    }

    private int calcularInicioDaLista(int page, int size, int total) {
        return Math.min(page * size, total);
    }

    private int calcularFinalDaLista(int fromIndex, int sizeNumber, int total) {
        return Math.min((fromIndex + sizeNumber), total);
    }
}

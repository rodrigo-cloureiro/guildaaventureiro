package br.com.infnet.guildaaventureiro.controller;

import br.com.infnet.guildaaventureiro.dto.*;
import br.com.infnet.guildaaventureiro.mapper.AventureiroMapper;
import br.com.infnet.guildaaventureiro.model.Aventureiro;
import br.com.infnet.guildaaventureiro.model.Companheiro;
import br.com.infnet.guildaaventureiro.service.AventureiroService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/aventureiro")
@Validated
public class AventureiroController {
    private final AventureiroMapper aventureiroMapper;
    private final AventureiroService aventureiroService;

    // ===================
    // Listar Aventureiros
    // ===================
    @GetMapping(value = "")
    public ResponseEntity<PagedResponse<AventureiroResponse>> listarAventureiros(
            @Valid AventureiroFiltroRequest filtro,
            @RequestHeader(value = "X-Page", required = false, defaultValue = "0")
            @Min(value = 0, message = "A página não pode ser negativa")
            int page,
            @RequestHeader(value = "X-Size", required = false, defaultValue = "25")
            @Range(min = 1, max = 50, message = "O tamanho deve ser entre 1 e 50")
            int size
    ) {
        PagedResponse<Aventureiro> paginaFiltrada = aventureiroService.listar(filtro, page, size);
        PagedResponse<AventureiroResponse> pagedResponse = new PagedResponse<>(
                paginaFiltrada.page(),
                paginaFiltrada.size(),
                paginaFiltrada.total(),
                paginaFiltrada.totalPages(),
                paginaFiltrada.content().stream()
                        .map(aventureiroMapper::toResponse)
                        .toList()
        );
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(paginaFiltrada.total()))
                .header("X-Page", String.valueOf(paginaFiltrada.page()))
                .header("X-Size", String.valueOf(paginaFiltrada.size()))
                .header("X-Total-Pages", String.valueOf(paginaFiltrada.totalPages()))
                .body(pagedResponse);
    }

    // =========================
    // Buscar Aventureiro por ID
    // =========================
    @GetMapping(value = "/{id}")
    public ResponseEntity<AventureiroResponse> buscarPorId(@PathVariable Long id) {
        Aventureiro aventureiro = aventureiroService.buscarPorId(id);
        return ResponseEntity.ok(aventureiroMapper.toResponse(aventureiro));
    }

    // =====================
    // Atualizar Aventureiro
    // =====================
    @PatchMapping(value = "/{id}")
    public ResponseEntity<AventureiroResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AventureiroUpdate update
    ) {
        Aventureiro atualizado = aventureiroService.atualizar(id, update);
        return ResponseEntity.ok(aventureiroMapper.toResponse(atualizado));
    }

    // ================================
    // Encerrar vinculo com Aventureiro
    // ================================
    @PatchMapping(value = "/{id}/encerrar-vinculo")
    public ResponseEntity<Void> encerrarVinculo(@PathVariable Long id) {
        aventureiroService.encerrarVinculo(id);
        return ResponseEntity.ok().build();
    }

    // ==============================
    // Recrutar Aventureiro novamente
    // ==============================
    @PatchMapping(value = "/{id}/recrutar")
    public ResponseEntity<Void> recrutarNovamente(@PathVariable Long id) {
        aventureiroService.recrutarNovamente(id);
        return ResponseEntity.noContent().build();
    }

    // =====================
    // Registrar Aventureiro
    // =====================
    @PostMapping(value = "")
    public ResponseEntity<AventureiroResponse> registrarAventureiro(@RequestBody @Valid AventureiroCreate dto) {
        Aventureiro aventureiro = aventureiroMapper.toEntity(dto);
        Aventureiro salvo = aventureiroService.criar(aventureiro);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aventureiroMapper.toResponse(salvo));
    }

    // ==================================
    // Definir Companheiro do Aventureiro
    // ==================================
    @PostMapping(value = "/{id}/companheiro")
    public ResponseEntity<Void> definirCompanheiro(
            @PathVariable Long id,
            @Valid @RequestBody Companheiro companheiro
    ) {
        Aventureiro aventureiro = aventureiroService.definirCompanheiro(id, companheiro);
        return ResponseEntity.noContent().build();
    }
}

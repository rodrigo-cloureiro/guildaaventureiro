package br.com.infnet.guildaaventureiro.util;

import br.com.infnet.guildaaventureiro.dto.PagedResponse;

import java.util.List;

public final class Paginator {
    public static <T> PagedResponse<T> paginate(List<T> content, int page, int size) {
        int total = content.size();

        int fromIndex = Math.min(page * size, total);
        int toIndex = Math.min((fromIndex + size), total);

        return new PagedResponse<>(
                page,
                size,
                total,
                content.subList(fromIndex, toIndex)
        );
    }
}

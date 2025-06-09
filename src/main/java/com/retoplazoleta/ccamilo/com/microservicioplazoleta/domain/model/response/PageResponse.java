package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageResponse<T> {
    private final List<T> content;
    private final int currentPage;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;
    private final boolean hasNext;
    private final boolean hasPrevious;
}

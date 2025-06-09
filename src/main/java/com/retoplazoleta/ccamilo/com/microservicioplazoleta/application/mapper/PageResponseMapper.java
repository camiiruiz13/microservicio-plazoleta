package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.mapper;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response.PageResponseDTO;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageResponseMapper{

    private PageResponseMapper() {
    }

    /**
     * Mapea un PageResponse<T> del dominio a un PageResponseDTO<R> de salida.
     *
     * @param pageModel página del dominio con contenido de tipo T
     * @param mapper    función para convertir T a R
     * @param <T>       tipo original (modelo de dominio)
     * @param <R>       tipo de salida (DTO)
     * @return PageResponseDTO<R>
     */
    public static <T, R> PageResponseDTO<R> toResponseDTO(PageResponse<T> pageModel, Function<T, R> mapper) {
        List<R> mappedContent = pageModel.getContent().stream()
                .map(mapper)
                .collect(Collectors.toList());

        return PageResponseDTO.<R>builder()
                .content(mappedContent)
                .currentPage(pageModel.getCurrentPage())
                .pageSize(pageModel.getPageSize())
                .totalElements(pageModel.getTotalElements())
                .totalPages(pageModel.getTotalPages())
                .hasNext(pageModel.isHasNext())
                .hasPrevious(pageModel.isHasPrevious())
                .build();
    }
}

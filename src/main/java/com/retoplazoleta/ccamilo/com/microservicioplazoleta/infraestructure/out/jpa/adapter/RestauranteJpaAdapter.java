package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.adapter;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.Restaurante;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.PageResponse;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi.IRestaurantePersitencePort;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.repositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersitencePort {

    private final RestauranteRepository repository;
    private  final IRestauranteEntityMapper restauranteEntityMapper;

    @Override
    public Restaurante saveRestaurante(Restaurante restaurante) {
        RestauranteEntity restauranteEntity = repository.save(restauranteEntityMapper.toRestauranteEntity(restaurante));
        return restauranteEntityMapper.toRestauranteModel(restauranteEntity);
    }

    @Override
    public Restaurante findByIdAndIdPropietario(Long idRestaurante, Long idPropietario) {
        return repository.findByIdAndIdPropietario(idRestaurante, idPropietario)
                .map(restauranteEntityMapper::toRestauranteModel)
                .orElse(null);
    }

    @Override
    public PageResponse<Restaurante> findAllRestaurantes(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize, Sort.by( "nombre"));
        Page<RestauranteEntity> findAllRestaurantEntitie = repository.findAll(pageable);
        List<Restaurante> listRestaurts = findAllRestaurantEntitie.getContent().stream()
                .map(restauranteEntityMapper::toRestauranteModel)
                .collect(Collectors.toList());

         return PageResponse.<Restaurante>builder()
                .content(listRestaurts)
                .currentPage(findAllRestaurantEntitie.getNumber())
                .pageSize(findAllRestaurantEntitie.getSize())
                .totalElements(findAllRestaurantEntitie.getTotalElements())
                .totalPages(findAllRestaurantEntitie.getTotalPages())
                .hasNext(findAllRestaurantEntitie.hasNext())
                .hasPrevious(findAllRestaurantEntitie.hasPrevious())
                .build();
    }


}

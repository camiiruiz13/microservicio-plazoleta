package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PLATOS")
public class PlatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_categoria",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_plato_categoria")
    )
    private CategoriaEntity categoria;

    @Column(name = "descripcion", nullable = true)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_restaurante",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_plato_restaurante")
    )
    private RestauranteEntity restaurante;

    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;

    @Column(name = "activo", nullable = true)
    private Boolean activo;

    @PrePersist
    public void prePersist() {
        if (activo == null) {
            activo = true;
        }
    }


}
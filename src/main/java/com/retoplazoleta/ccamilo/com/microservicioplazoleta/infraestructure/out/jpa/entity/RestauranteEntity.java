package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RESTAURANTES")
public class RestauranteEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "id_propietario")
    private Long idPropietario;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "url_logo")
    private String urlLogo;

    @Column(name = "nit")
    private String nit;

}
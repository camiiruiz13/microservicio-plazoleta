package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PEDIDO_PLATO")
public class PedidoPlatoEntity {


    @EmbeddedId
    private PedidoPlatoIdEmbeded id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPedido")
    @JoinColumn(
            name = "id_pedido",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_plato_pedido")
    )
    private PedidoEntity pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPlato")
    @JoinColumn(
            name = "id_plato",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_plato_plato")
    )
    private PlatoEntity plato;


    @Column(name = "cantidad")
    private Integer cantidad;

}
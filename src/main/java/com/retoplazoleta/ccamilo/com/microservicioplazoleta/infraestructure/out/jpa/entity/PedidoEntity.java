package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PEDIDOS")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "fecha")
    private String fecha;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @Column(name = "id_chef")
    private Long idChef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_restaurante",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_restaurante")
    )
    private RestauranteEntity restaurante;

}
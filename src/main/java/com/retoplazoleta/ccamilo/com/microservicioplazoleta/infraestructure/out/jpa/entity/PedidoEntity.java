package com.retoplazoleta.ccamilo.com.microservicioplazoleta.infraestructure.out.jpa.entity;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.EstadoPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDate fecha;

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

    @OneToMany(mappedBy = "pedido")
    private List<PedidoPlatoEntity> platos;

    @Column(name = "pin_seguridad", length = 4)
    private String pinSeguridad;


    @PrePersist
    public void prePersist() {
        this.fecha = LocalDate.now();
        if (this.estado == null) {
            this.estado = EstadoPedido.PENDIENTE;
        }
    }


}
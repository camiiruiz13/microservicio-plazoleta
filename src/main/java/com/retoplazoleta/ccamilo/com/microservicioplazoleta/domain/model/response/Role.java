package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idRol;
    private String nombre;
    private String descripcion;


}

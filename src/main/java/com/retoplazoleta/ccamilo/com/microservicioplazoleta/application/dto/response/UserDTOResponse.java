package com.retoplazoleta.ccamilo.com.microservicioplazoleta.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTOResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String numeroDocumento;
    private String celular;
    private String fechaNacimiento;
    private String correo;
    private RoleDTO rol;
}

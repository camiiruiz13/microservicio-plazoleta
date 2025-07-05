package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.spi;

import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.request.TraceLog;
import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.model.response.User;

import java.util.List;

public interface IApiClientPort {

    Long idPropietario(String correo,  String token);
    Long idPropietario(String correo,  String token, Object model);
    User idUser(Long idUser, String token);
    void notificarUser(String celular, String codigo, String token);
    void crearTraza(TraceLog traceLog, String token);
    List<User> fetchEmployeesAndClients(List<Long> clientIds, List<Long> chefIds , String token);
}

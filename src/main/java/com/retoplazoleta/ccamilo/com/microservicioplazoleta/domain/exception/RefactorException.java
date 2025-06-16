package com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.exception;


import com.retoplazoleta.ccamilo.com.microservicioplazoleta.domain.constants.ValidationConstant;

public class RefactorException extends DomainException {

    public RefactorException(ValidationConstant validationConstant, Long id) {
        super(validationConstant.getMessage() + id);
    }

    public RefactorException(ValidationConstant validationConstant) {
        super(validationConstant.getMessage());
    }
}


package com.ecommpay.web;

import com.ecommpay.BalanceException;
import com.ecommpay.web.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.ecommpay.AppCodes.SYSTEM_ERROR;

@ControllerAdvice
class ExceptionHandlingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(BalanceException.class)
    @ResponseBody
    public Response balanceException(BalanceException e) {
        LOGGER.warn("Balance exception", e);
        return new Response(e.getCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response balanceException(Exception e) {
        LOGGER.warn("Exception", e);
        return new Response(SYSTEM_ERROR);
    }


}

package com.ecommpay.web;

import com.ecommpay.BalanceException;
import com.ecommpay.web.dto.BaseResponse;
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
    public BaseResponse balanceException(BalanceException e) {
        LOGGER.warn("Balance exception", e);
        return new BaseResponse(e.getCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse balanceException(Exception e) {
        LOGGER.warn("Exception", e);
        return new BaseResponse(SYSTEM_ERROR);
    }


}

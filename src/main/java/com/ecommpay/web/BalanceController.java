package com.ecommpay.web;


import com.ecommpay.BalanceException;
import com.ecommpay.repository.BalanceRepository;
import com.ecommpay.web.dto.Request;
import com.ecommpay.web.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

import static com.ecommpay.AppCodes.SUCCESS;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class BalanceController {

    private final BalanceRepository repository;

    @Autowired
    public BalanceController(BalanceRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = POST, consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public Response post(@RequestBody @Valid Request request) {
        switch (request.getRequestType()) {
            case "CREATE-AGT":
                repository.createAgent(request.getLogin(), request.getPassword());
                return new Response(SUCCESS);
            case "GET-BALANCE":
                BigDecimal balance = repository.getBalanceIfPasswordIsValid(request.getLogin(), request.getPassword());
                return new Response(SUCCESS).add("balance", balance);
            default:
                //т.к. у нас нету специального кода для такой ситуации, то кидаем системную ошибку
                throw BalanceException.systemError();
        }

    }


}

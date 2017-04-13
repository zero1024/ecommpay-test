package com.ecommpay;

import static com.ecommpay.AppCodes.*;

public class BalanceException extends RuntimeException {

    //-------------------------Конструкторы-----------------------//

    public static BalanceException agentAlreadyExists() {
        return new BalanceException(AGENT_ALREADY_EXISTS);
    }

    public static BalanceException systemError() {
        return new BalanceException(SYSTEM_ERROR);
    }

    public static BalanceException agentNotFound() {
        return new BalanceException(AGENT_NOT_FOUND);
    }

    public static BalanceException incorrectPassword() {
        return new BalanceException(INCORRECT_PASSWORD);
    }


    //------------------------Сам класс--------------------------//

    private int code;

    private BalanceException(int code) {
        super(String.format("Error code:[%s]", code));
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}

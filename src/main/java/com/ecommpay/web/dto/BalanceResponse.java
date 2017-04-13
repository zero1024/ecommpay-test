package com.ecommpay.web.dto;

import java.math.BigDecimal;

public class BalanceResponse extends BaseResponse {

    private BigDecimal balance;

    public BalanceResponse(Integer resultCode, BigDecimal balance) {
        super(resultCode);
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}

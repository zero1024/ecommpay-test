package com.ecommpay.repository;

import java.math.BigDecimal;

public interface BalanceRepository {

    void createAgent(String login, String password);

    BigDecimal getBalanceIfPasswordIsValid(String login, String password);

}

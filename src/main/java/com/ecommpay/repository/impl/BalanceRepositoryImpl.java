package com.ecommpay.repository.impl;

import com.ecommpay.BalanceException;
import com.ecommpay.repository.BalanceRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class BalanceRepositoryImpl implements BalanceRepository {

    private static final int BCRYPT_LOG_ROUNDS = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceRepositoryImpl.class);
    private final JdbcTemplate template;

    @Autowired
    public BalanceRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void createAgent(String login, String password) {
        try {
            LOGGER.info("Creating new agent with login [{}]", login);
            template.update("INSERT INTO AGENT(LOGIN, PASSWORD) VALUES (?,?)", login, hashPassword(password));
        } catch (DataIntegrityViolationException e) {
            checkUniqueLoginConstraint(e);
            throw e;
        }
    }


    @Override
    public BigDecimal getBalanceIfPasswordIsValid(String login, String password) {
        try {
            LOGGER.info("Getting balance for agent [{}]", login);
            return template.queryForObject("SELECT PASSWORD,BALANCE FROM AGENT WHERE LOGIN = ?", (rs, rowNum) -> {
                validatePassword(password, rs.getString("PASSWORD"));
                return rs.getBigDecimal("BALANCE");
            }, login);
        } catch (EmptyResultDataAccessException e) {
            throw BalanceException.agentNotFound();
        }
    }


    private static void validatePassword(String password, String hashedPassword) {
        if (!BCrypt.checkpw(password, hashedPassword)) {
            throw BalanceException.incorrectPassword();
        }
    }

    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_LOG_ROUNDS));
    }

    private static void checkUniqueLoginConstraint(DataIntegrityViolationException e) {
        if (e.getMessage().contains("UNIQUE_LOGIN")) {
            //на всякий случай логируем
            LOGGER.warn(e.getMessage(), e);
            throw BalanceException.agentAlreadyExists();
        }
    }

}

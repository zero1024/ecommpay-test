package com.ecommpay.web.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.validation.constraints.NotNull;

public class Request {

    @JacksonXmlProperty(localName = "request-type")
    @NotNull
    private String requestType;
    @NotNull
    private String login;
    @NotNull
    private String password;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

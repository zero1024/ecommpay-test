package com.ecommpay.web.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.LinkedHashMap;

@JacksonXmlRootElement(localName = "response")
public class Response extends LinkedHashMap<String, Object> {

    public Response(Integer resultCode) {
        add("result-code", resultCode);
    }

    public Response add(String key, Object value) {
        put(key, value);
        return this;
    }


}

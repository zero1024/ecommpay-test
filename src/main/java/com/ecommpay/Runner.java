package com.ecommpay;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


@SpringBootApplication(scanBasePackages = "com.ecommpay")
public class Runner extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        XmlMapper mapper = builder.createXmlMapper(true).build();
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        converters.add(new MappingJackson2XmlHttpMessageConverter(mapper));
    }

}

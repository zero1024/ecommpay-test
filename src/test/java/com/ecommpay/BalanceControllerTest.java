package com.ecommpay;

import org.assertj.core.util.Files;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BalanceControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    static {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void successTest() throws Exception {
        //1. создаем агента
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        ResponseEntity<String> res = restTemplate.exchange("/balance", POST,
                new HttpEntity<>(content("successTest1.xml"), headers),
                String.class);

        assert res.getStatusCodeValue() == 200;
        assert XMLUnit.compareXML(res.getBody(), content("responseWithCode0.xml")).identical();

        //2. проверяем баланс
        res = restTemplate.exchange("/balance", POST,
                new HttpEntity<>(content("successTest2.xml"), headers),
                String.class);

        assert res.getStatusCodeValue() == 200;
        assert XMLUnit.compareXML(res.getBody(), content("responseWithBalance0.xml")).identical();

        //3. проверяем баланс у пользователя у которого есть деньги на счету
        res = restTemplate.exchange("/balance", POST,
                new HttpEntity<>(content("successTest3.xml"), headers),
                String.class);

        assert res.getStatusCodeValue() == 200;
        assert XMLUnit.compareXML(res.getBody(), content("responseWithBalance100.xml")).identical();
    }

    @Test
    public void incorrectBodyTest() throws Exception {
        for (int i = 1; i < 4; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            ResponseEntity<String> res = restTemplate.exchange("/balance", POST,
                    new HttpEntity<>(content("incorrectBodyTest" + i + ".xml"), headers),
                    String.class);

            assert res.getStatusCodeValue() == 200;
            assert XMLUnit.compareXML(res.getBody(), content("responseWithCode2.xml")).identical();
        }

    }

    private static String content(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        try {
            return Files.contentOf(resource.getFile(), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

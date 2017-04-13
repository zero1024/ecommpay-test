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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        ResponseEntity<String> res1 = restTemplate.exchange("/balance", POST,
                new HttpEntity<>(content("successTest1.xml"), headers),
                String.class);

        assert res1.getStatusCodeValue() == 200;
        assert XMLUnit.compareXML(res1.getBody(), content("response_0.xml")).identical();
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

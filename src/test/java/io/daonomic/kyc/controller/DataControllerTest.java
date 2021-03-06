package io.daonomic.kyc.controller;

import io.daonomic.kyc.AbstractWebIntegrationTest;
import io.daonomic.kyc.client.DaonomicClient;
import io.daonomic.kyc.domain.TestData;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class DataControllerTest extends AbstractWebIntegrationTest {
    @Autowired
    private DaonomicClient daonomicClient;

    public void setAndGet() {
        String id = RandomStringUtils.randomAlphabetic(10);
        when(daonomicClient.notifyDaonomic(id)).thenReturn(Mono.empty());
        TestData data = new TestData(
            "Country",
            "First",
            "Last",
            "26.07.1912",
            "0000",
            "Post",
            true,
            Arrays.asList("pass"),
            Arrays.asList("selfie"),
            Arrays.asList(),
            RandomStringUtils.randomAlphabetic(10)
        );
        restTemplate.postForObject(baseUrl + "/users/{id}", data, Void.class, id);
        verify(daonomicClient).notifyDaonomic(id);
        TestData response = restTemplate.getForObject(baseUrl + "/users/{id}", TestData.class, id);
        assertEquals(response.getAddress(), data.getAddress());
        assertEquals(response.getCountry(), "Country");
    }

    public void notFound() {
        try {
            restTemplate.getForObject(baseUrl + "/users/{id}", TestData.class, RandomStringUtils.randomAlphabetic(10));
            fail("should be error");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}

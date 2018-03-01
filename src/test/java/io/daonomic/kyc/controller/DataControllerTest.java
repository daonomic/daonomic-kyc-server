package io.daonomic.kyc.controller;

import io.daonomic.kyc.AbstractWebIntegrationTest;
import io.daonomic.kyc.domain.TestData;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

public class DataControllerTest extends AbstractWebIntegrationTest {
    public void setAndGet() {
        String id = UUID.randomUUID().toString();
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
        TestData response = restTemplate.getForObject(baseUrl + "/users/{id}", TestData.class, id);
        assertEquals(response.getAddress(), data.getAddress());
        assertEquals(response.getCountry(), "Country");
    }
}

package io.daonomic.kyc.controller;

import io.daonomic.kyc.AbstractWebIntegrationTest;
import io.daonomic.kyc.common.TestResource;
import io.daonomic.kyc.domain.UploadResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class FilesControllerTest extends AbstractWebIntegrationTest {
    @Test
    public void uploadAndDownload() {
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        byte[] data = RandomUtils.nextBytes(1000000);
        String filename = RandomStringUtils.randomAlphabetic(10);
        HttpHeaders fileHeaders = new HttpHeaders();
        fileHeaders.setContentType(MediaType.IMAGE_PNG);
        HttpEntity<Resource> fileEntity = new HttpEntity<>(getResource(data, filename), fileHeaders);
        bodyMap.add("file", fileEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        UploadResult[] result = restTemplate.exchange(baseUrl + "/files", HttpMethod.POST, requestEntity, UploadResult[].class).getBody();
        assertEquals(result.length, 1);

        ResponseEntity<byte[]> download = restTemplate
            .exchange(baseUrl + "/files/{id}", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), byte[].class, result[0].getId());

        assertEquals(download.getBody(), data);
        assertEquals(download.getHeaders().get("content-type"), Collections.singletonList("image/png"));
    }

    public void notFound() {
        try {
            restTemplate.getForObject(baseUrl + "/files/{id}", byte[].class, RandomStringUtils.randomAlphabetic(10));
            fail("should be error");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    private Resource getResource(byte[] data, String filename) {
        return new TestResource(data, filename);
    }
}

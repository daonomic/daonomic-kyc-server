package io.daonomic.kyc.controller;

import io.daonomic.kyc.client.DaonomicClient;
import io.daonomic.kyc.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class DataController {
    @Autowired
    private FileService fileService;
    @Value("${dataPath:/var/kyc}")
    private String path;
    @Autowired
    private DaonomicClient daonomicClient;

    @PostMapping(value = "/users/{id}", consumes = "application/json")
    public Mono<Void> setData(@PathVariable String id, @RequestBody String body) {
        return fileService.writeSmallFile(getPath().resolve(id + ".json"), body.getBytes(StandardCharsets.UTF_8))
            .then(daonomicClient.notifyDaonomic(id));
    }

    @GetMapping(value = "/users/{id}", produces = "application/json")
    public Mono<String> getData(@PathVariable String id) {
        return fileService.readSmallFile(getPath().resolve(id + ".json"))
            .map(bytes -> new String(bytes, StandardCharsets.UTF_8));
    }

    private Path getPath() {
        return Paths.get(path);
    }
}

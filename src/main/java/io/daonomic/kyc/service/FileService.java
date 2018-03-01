package io.daonomic.kyc.service;

import reactor.core.publisher.Mono;

import java.nio.file.Path;

public interface FileService {
    Mono<byte[]> readSmallFile(Path path);
    Mono<Void> writeSmallFile(Path path, byte[] content);
}

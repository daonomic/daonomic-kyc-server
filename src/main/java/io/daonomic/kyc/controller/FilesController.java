package io.daonomic.kyc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.daonomic.kyc.domain.Metadata;
import io.daonomic.kyc.domain.MyFileSystemResource;
import io.daonomic.kyc.domain.UploadResult;
import io.daonomic.kyc.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

@RestController
public class FilesController {
    public static final Logger logger = LoggerFactory.getLogger(FilesController.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${dataPath:/var/kyc}")
    private String path;
    @Autowired
    private FileService fileService;
    private final SecureRandom random = new SecureRandom();

    @PostMapping(path = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flux<UploadResult> uploadFile(@RequestBody Flux<Part> parts) {
        long start = System.nanoTime();
        Flux<UploadResult> result = parts
            .filter(part -> part instanceof FilePart)
            .flatMap(part -> {
                try {
                    return uploadFile(((FilePart) part));
                } catch (IOException e) {
                    return Mono.error(e);
                }
            });
        logger.info("completed in {} nanos", System.nanoTime() - start);
        return result;
    }

    private Mono<UploadResult> uploadFile(FilePart part) throws IOException {
        final String id = generateId();
        return fileService.writeSmallFile(getPath().resolve(id + ".metadata"), objectToJson(new Metadata(part.filename(), Optional.ofNullable(part.headers().getContentType()).map(MimeType::toString).orElse(null))))
            .then(DataBufferUtils.write(part.content(), AsynchronousFileChannel.open(getPath().resolve(id + ".content"), WRITE, CREATE), 0).collectList())
            .then(Mono.just(new UploadResult(id)));
    }

    @GetMapping("/files/{id}")
    public Mono<Resource> downloadFile(@PathVariable String id, ServerHttpResponse response) {
        return fileService.readSmallFile(getPath().resolve(id + ".metadata"))
            .map(content -> jsonToObject(content, Metadata.class))
            .map(metadata -> {
                response.getHeaders().put("content-type", Collections.singletonList(metadata.getContentType()));
                return new MyFileSystemResource(new File(path, id + ".content"), metadata.getFilename());
            });
    }

    private String generateId() {
        byte[] id = new byte[32];
        random.nextBytes(id);
        return new BigInteger(1, id).toString(16);
    }

    private byte[] objectToJson(Object t) {
        try {
            return objectMapper.writeValueAsString(t).getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T jsonToObject(byte[] content, Class<T> tClass) {
        try {
            return objectMapper.readValue(new String(content, StandardCharsets.UTF_8), tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getPath() {
        return Paths.get(path);
    }
}

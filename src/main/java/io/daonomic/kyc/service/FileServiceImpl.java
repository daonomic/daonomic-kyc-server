package io.daonomic.kyc.service;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.*;

@Service
public class FileServiceImpl implements FileService {
    private final DefaultDataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();

    @Override
    public Mono<byte[]> readSmallFile(Path path) {
        return DataBufferUtils.readAsynchronousFileChannel(() -> AsynchronousFileChannel.open(path, READ), new DefaultDataBufferFactory(), 10000)
            .map(DataBuffer::asByteBuffer)
            .collectList()
            .map(this::toByteArray);
    }

    @Override
    public Mono<Void> writeSmallFile(Path path, byte[] content) {
        return open(path)
            .flatMap(channel -> writeSmallFile(channel, content));
    }

    private Mono<Void> writeSmallFile(AsynchronousFileChannel channel, byte[] content) {
        return DataBufferUtils.write(Mono.just(dataBufferFactory.wrap(content)), channel, 0).then();
    }

    private Mono<AsynchronousFileChannel> open(Path path) {
        try {
            return Mono.just(AsynchronousFileChannel.open(path, WRITE, CREATE));
        } catch (IOException e) {
            return Mono.error(e);
        }
    }

    private byte[] toByteArray(final List<ByteBuffer> buffers) {
        final byte[] bytes = new byte[buffers.stream().mapToInt(Buffer::remaining).sum()];
        buffers.forEach(b -> {
            byte[] current = new byte[b.remaining()];
            b.get(current);
            System.arraycopy(current, 0, bytes, 0, current.length);
        });
        return bytes;
    }
}

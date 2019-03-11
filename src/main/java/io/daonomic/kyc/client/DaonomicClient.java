package io.daonomic.kyc.client;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

public class DaonomicClient {

    private final WebClient client;

    public DaonomicClient(String baseUrl, int connectTimeout, int readTimeout) {
        TcpClient client = TcpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
            .doOnConnected(c -> {
                c.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS));
                c.addHandlerLast(new WriteTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS));
            });

        this.client = WebClient.builder()
            .baseUrl(baseUrl)
            .clientConnector(new ReactorClientHttpConnector(HttpClient.from(client)))
            .build();
    }

    public Mono<Void> notifyDaonomic(String userId) {
        ObjectNode form = JsonNodeFactory.instance.objectNode().put("status", "ON_REVIEW");
        return client.post().uri("/kyc/selfService/{id}/webhook", userId)
            .body(BodyInserters.fromObject(form))
            .exchange()
            .then();
    }
}

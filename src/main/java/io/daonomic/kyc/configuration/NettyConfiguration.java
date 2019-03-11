package io.daonomic.kyc.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.DisposableServer;
import reactor.netty.http.HttpResources;
import reactor.netty.http.server.HttpServer;
import reactor.netty.resources.LoopResources;

@Configuration
public class NettyConfiguration {
    public static final Logger logger = LoggerFactory.getLogger(NettyConfiguration.class);

    @Value("${httpPort:8080}")
    private int httpPort;
    @Autowired
    private ApplicationContext context;

    @Bean(destroyMethod = "dispose")
    public DisposableServer server() {
        logger.info("Web port: {}", httpPort);
        HttpHandler httpHandler = WebHttpHandlerBuilder.applicationContext(context).build();
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        LoopResources loopResources = LoopResources.create("reactor", LoopResources.DEFAULT_IO_WORKER_COUNT, false);
        HttpResources.set(loopResources);
        return HttpServer.create().host("0.0.0.0").port(httpPort).handle(adapter).bindNow();
    }
}

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
import reactor.ipc.netty.NettyContext;
import reactor.ipc.netty.http.HttpResources;
import reactor.ipc.netty.http.server.HttpServer;
import reactor.ipc.netty.resources.LoopResources;

@Configuration
public class NettyConfiguration {
    public static final Logger logger = LoggerFactory.getLogger(NettyConfiguration.class);

    @Value("${httpPort:8080}")
    private int httpPort;
    @Autowired
    private ApplicationContext context;

    @Bean(destroyMethod = "dispose")
    public NettyContext server() {
        logger.info("Web port: {}", httpPort);
        HttpHandler httpHandler = WebHttpHandlerBuilder.applicationContext(context).build();
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        LoopResources loopResources = LoopResources.create("reactor", LoopResources.DEFAULT_IO_WORKER_COUNT, false);
        HttpResources.set(loopResources);
        HttpServer server = HttpServer.create("0.0.0.0", httpPort);
        return server.newHandler(adapter).block();
    }
}

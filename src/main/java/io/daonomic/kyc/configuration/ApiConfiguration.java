package io.daonomic.kyc.configuration;

import io.daonomic.kyc.Kyc;
import io.daonomic.kyc.client.DaonomicClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@Configuration
@Import({
    NettyConfiguration.class,
    WebConfiguration.class
})
@ComponentScan(basePackageClasses = Kyc.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class))
public class ApiConfiguration extends PropertySourceConfiguration {

    @Bean
    public DaonomicClient daonomicClient(
        @Value("${daonomicApiUrl:https://api.daonomic.io/v1}") String baseUrl
    ) {
        return new DaonomicClient(baseUrl, 10000, 10000);
    }
}

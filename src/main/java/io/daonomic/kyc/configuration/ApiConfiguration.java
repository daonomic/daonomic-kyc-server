package io.daonomic.kyc.configuration;

import io.daonomic.kyc.Kyc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@Configuration
@Import({
    NettyConfiguration.class,
    WebConfiguration.class
})
@ComponentScan(basePackageClasses = Kyc.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class))
public class ApiConfiguration extends PropertySourceConfiguration {

}

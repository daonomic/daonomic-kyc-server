package io.daonomic.kyc.mock;

import io.daonomic.kyc.client.DaonomicClient;
import io.daonomic.kyc.configuration.ApiConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
@Import(ApiConfiguration.class)
public class MockContext {
    @Bean
    @Primary
    public DaonomicClient daonomicClient() {
        return mock(DaonomicClient.class);
    }
}

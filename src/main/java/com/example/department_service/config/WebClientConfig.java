package com.example.department_service.config;

import com.example.department_service.client.EmployeeClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    private final LoadBalancedExchangeFilterFunction  loadBalanceFilterFunction;

    public WebClientConfig(LoadBalancedExchangeFilterFunction loadBalanceFilterFunction) {
        this.loadBalanceFilterFunction = loadBalanceFilterFunction;
    }

    @Bean
    public WebClient employeeWebClient() {
        return WebClient.builder()
                .baseUrl("http://employee-service")
                .filter(loadBalanceFilterFunction)
                .build();
    }

    @Bean
    public EmployeeClient employeeClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(employeeWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(EmployeeClient.class);
    }

}

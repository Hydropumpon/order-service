package com.example.orderservice.config;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ProbabilisticSampler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


@org.springframework.context.annotation.Configuration
public class JaegerConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public io.opentracing.Tracer jaegerTracer() {

        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType(
                ProbabilisticSampler.TYPE).withParam(1);
        Configuration.ReporterConfiguration reporterConfiguration =
                Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration configuration =
                new Configuration(appName).withSampler(samplerConfiguration).withReporter(reporterConfiguration);

        return configuration.getTracer();
    }
}

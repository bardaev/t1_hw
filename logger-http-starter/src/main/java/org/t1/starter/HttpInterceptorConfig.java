package org.t1.starter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.t1.logger.HttpLogger;

@Configuration
@RequiredArgsConstructor
public class HttpInterceptorConfig implements WebMvcConfigurer {

    private final HttpLogger httpLogger;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpLogger);
    }
}

package com.gmail.vladbaransky.webmodule.config;

import com.gmail.vladbaransky.service.util.CountryIdentifier;
import com.gmail.vladbaransky.webmodule.listener.SessionSetterListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSessionListener;

@Configuration
public class ListenerConfig {
    private final CountryIdentifier countryIdentifier;

    public ListenerConfig(CountryIdentifier countryIdentifier) {
        this.countryIdentifier = countryIdentifier;
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> listenerRegistrationBean() {
        ServletListenerRegistrationBean<HttpSessionListener> bean =
                new ServletListenerRegistrationBean<>();
        bean.setListener(new SessionSetterListener(countryIdentifier));
        return bean;
    }
}

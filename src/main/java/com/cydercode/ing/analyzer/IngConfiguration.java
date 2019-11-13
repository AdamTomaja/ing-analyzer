package com.cydercode.ing.analyzer;

import com.cydercode.ing.client.EasyIng;
import com.cydercode.ing.client.IngConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cydercode.ing.client.Configuration.*;

@Configuration
public class IngConfiguration {

    @Value("${ing.url}")
    private String url;

    @Value("${ing.username}")
    private String username;

    @Value("${ing.password}")
    private String password;

    @Bean
    public EasyIng easyIng() {
        IngConnector ingConnector = new IngConnector(builder()
                .url(url)
                .login(username)
                .password(password)
                .build());
        return new EasyIng(ingConnector);
    }
}

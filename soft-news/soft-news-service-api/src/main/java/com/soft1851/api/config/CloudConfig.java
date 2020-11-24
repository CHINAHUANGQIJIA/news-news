package com.soft1851.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author wl_sun
 * @description TODO
 * @Data 2020/11/23
 */

@Configuration
public class CloudConfig {

    public CloudConfig(){}


    /**
     * 基于OKHttp3配置RestTemplate
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

}

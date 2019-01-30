/**
 * <p>
 * Title: RestTemplateConfig.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2019
 * </p>
 * <p>
 * Company: Sharp
 * </p>
 * 
 * @Project Name: SpringBootCommonLib
 * @Package: com.srct.service.config.connection
 * @author sharp
 * @date 2019-01-22 15:26:04
 */
package com.srct.service.config.connection;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.srct.service.exception.ServiceException;
import com.srct.service.utils.log.Log;

/**
 * @author sharp
 *
 */
@Configuration
public class RestTemplateConfig {

    @Value("${my.rest.proxy.enable}")
    private boolean proxyEnable;
    @Value("${my.rest.proxy.host}")
    private String proxyHost;
    @Value("${my.rest.proxy.port}")
    private String proxyPort;
    @Value("${my.rest.config.requesttimeout}")
    private int requestTimeout;
    @Value("${my.rest.config.connecttimeout}")
    private int connectTimeout;
    @Value("${my.rest.config.readtimeout}")
    private int readTimeout;

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(connectTimeout);
        httpRequestFactory.setReadTimeout(readTimeout);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
            new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter
            .setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
        restTemplate.getMessageConverters().add(1, mappingJackson2HttpMessageConverter);
        restTemplate.setInterceptors(Collections.singletonList(new AgentInterceptor()));
        restTemplate.setErrorHandler(responseErrorHandler);
        return restTemplate;
    }

    public class AgentInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
            HttpHeaders headers = request.getHeaders();
            if (headers.getContentType() == null) {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }
            return execution.execute(request, body);
        }
    }

    private ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {

        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            if (clientHttpResponse.getStatusCode().equals(HttpStatus.OK)) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            String repErorrString = StreamUtils.copyToString(clientHttpResponse.getBody(), Charset.forName("UTF-8"));
            Log.i("resp = {}", repErorrString);
            throw new ServiceException(repErorrString);
        }
    };

}

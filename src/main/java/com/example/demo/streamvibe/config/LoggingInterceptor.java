package com.example.demo.streamvibe.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        log.info("\n\nRequest: {} {} \nHeaders\n{} \nBody\n{}\n",
                request.getMethod(), request.getURI(),
                request.getHeaders(),
                new String(body));

        BufferingClientHttpResponseWrapper response = new BufferingClientHttpResponseWrapper(execution.execute(request, body));

        log.info("\nResponse: {} {} \nHeaders\n{} \nBody\n{}",
                response.getRawStatusCode(), response.getStatusText(),
                response.getHeaders(),
                response.getResponse());

        return response;
    }

}

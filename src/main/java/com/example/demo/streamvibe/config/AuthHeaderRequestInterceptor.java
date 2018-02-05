package com.example.demo.streamvibe.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AuthHeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthHeaderRequestInterceptor.class);

    private final String headerName;
    private final String headerValue;

    public AuthHeaderRequestInterceptor(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequest wrapper = new HttpRequestWrapper(request);
        wrapper.getHeaders().set(headerName, headerValue);

        log.info("\n\nRequest: {} {} \nHeaders\n{} \nBody\n{}\n",
                request.getMethod(), request.getURI(),
                request.getHeaders(),
                new String(body));

        ClientHttpResponse response = execution.execute(wrapper, body);
        log.info("\nResponse: {} {} \nHeaders\n{} \nBody\n{}",
                response.getRawStatusCode(), response.getStatusText(),
                response.getHeaders(),
                new BufferedReader(new InputStreamReader(response.getBody())).readLine());

        return response;
    }

}

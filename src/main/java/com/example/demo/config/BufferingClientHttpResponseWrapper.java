package com.example.demo.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

    private final ClientHttpResponse response;

    private byte[] body;


    BufferingClientHttpResponseWrapper(ClientHttpResponse response) {
        this.response = response;
    }


    @Override
    public HttpStatus getStatusCode() throws IOException {
        return this.response.getStatusCode();
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return this.response.getRawStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return this.response.getStatusText();
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.response.getHeaders();
    }

    @Override
    public InputStream getBody() throws IOException {
        if (this.body == null) {
            this.body = StreamUtils.copyToByteArray(this.response.getBody());
        }
        return new ByteArrayInputStream(this.body);
    }

    public String getResponse() throws IOException {
        //this.body = new BufferedReader(new InputStreamReader(this.response.getBody())).readLine().getBytes();

        if (this.body == null) {
            this.body = StreamUtils.copyToByteArray(this.response.getBody());
        }
        return new String(body);
    }

    @Override
    public void close() {
        this.response.close();
    }

}
package com.example.demo.transport.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pavlovskii-pc on 22/12/2017.
 */
@Data
@NoArgsConstructor
public class BaseRequest {

    String method;


    public enum Method {
        getApiToken,
        createStream,
        registerStream,
        scheduleStream,
        listStreams,
        unregisterStream
    }
}

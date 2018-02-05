package com.example.demo.streamvibe.transport.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pavlovskii-pc on 11/01/2018.
 */
@Data
public class GetStreamResponse extends BaseResponse {

    String status;
    Result result;

    public GetStreamResponse(){

    }

    @Data
    @NoArgsConstructor
    public class Result {
        Stream stream;

    }
    @Data
    @NoArgsConstructor
    public static class Stream {
        String name;
        String id;
        String type;
        Integer options;
        Integer duration;
        String image;
        String videoUrl;
    }
}

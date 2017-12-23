package com.example.demo.transport.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by stas on 12/23/2017.
 */
@Data
public class ListStreamsResponse extends BaseResponse {

    String status;
    Result result;

    public ListStreamsResponse() {

    }

    @Data
    @NoArgsConstructor
    public class Result {
        List<Stream> streams;
        Integer totalRecords;

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

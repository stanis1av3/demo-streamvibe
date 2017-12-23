package com.example.demo.transport.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pavlovskii-pc on 22/12/2017.
 */
@Data
@NoArgsConstructor
public class CreateStreamResponse extends BaseResponse {

    String status;
    CreateStreamResponse.Result result;

    @Data
    @NoArgsConstructor
    public class Result {
        String streamId;
        String sourceUrl;
        String uploadUrl;

    }
}

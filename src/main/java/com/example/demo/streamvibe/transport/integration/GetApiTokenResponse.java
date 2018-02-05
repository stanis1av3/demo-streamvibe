package com.example.demo.streamvibe.transport.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pavlovskii-pc on 22/12/2017.
 */
@Data
@NoArgsConstructor
public class GetApiTokenResponse extends BaseResponse {

    String status;
    Result result;

    @Data
    @NoArgsConstructor
    public class Result{
        String token;
    }
}

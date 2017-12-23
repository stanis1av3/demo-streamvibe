package com.example.demo.transport.integration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by stas on 12/23/2017.
 */
@Data
@NoArgsConstructor
public class UnregisterStreamRequest extends BaseRequest {

    String method = Method.unregisterStream.name();
    Params params;

    public UnregisterStreamRequest(String token, String streamId){
        params = new Params(token, streamId, true);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Params {

        String token;
        String streamId;
        Boolean force;

    }

}

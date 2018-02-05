package com.example.demo.streamvibe.transport.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pavlovskii-pc on 22/12/2017.
 */
@Data
@NoArgsConstructor
public class CreateStreamRequest extends BaseRequest {

    String method = Method.createStream.name();

    CreateStreamRequest.Params params;

    public CreateStreamRequest(String token) {
        params = new Params(token);
    }

    @Data
    @NoArgsConstructor
    public class Params {
        String token;
        String streamType = "vod";
        String streamId = "";
        String[] options = {};
        String targetRegion = "europe";

        public Params(String token) {
            this.token = token;
        }
    }
}

package com.example.demo.transport.integration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pavlovskii-pc on 22/12/2017.
 */
@Data
@AllArgsConstructor
public class RegisterStreamRequest extends BaseRequest {

    String method = Method.registerStream.name();
    Params params;

    public RegisterStreamRequest(String token, String streamId, String sourceUrl, String streamName) {
        params = new Params(token, streamId, sourceUrl, streamName);
    }

    @Data
    @NoArgsConstructor
    public class Params {

        public Params(String token, String streamId, String sourceUrl, String streamName) {

            this.token = token;
            this.streamId = streamId;
            this.sourceUrl = sourceUrl;
            this.streamName = streamName;

        }

        String token;
        String streamType = "vod";
        String streamId;
        String streamName;
        String accessType = "free";
        Integer[] options = {};
        Integer viewMode=0;
        String sourceUrl;
        String targetRegion = "europe";
    }
}

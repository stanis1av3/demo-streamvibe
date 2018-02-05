package com.example.demo.streamvibe.transport.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pavlovskii-pc on 11/01/2018.
 */
@Data
@NoArgsConstructor
public class GetStreamRequest extends BaseRequest{

    GetStreamRequest.Params params;

    public GetStreamRequest(String token, String streamId){
        this.params=new Params(token, streamId);
        this.method=Method.getStream.name();
    }

    @Data
    @NoArgsConstructor
    public class Params {

        String token;
        String streamId;
        String imageSize;

        public Params(String token, String streamId) {
            this.token = token;
            this.streamId = streamId;
            this.imageSize="big";
        }


    }
}

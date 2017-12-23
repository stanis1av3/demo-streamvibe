package com.example.demo.transport.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by stas on 12/23/2017.
 */
@Data
@NoArgsConstructor
public class ListStreamsRequest extends BaseRequest {

    String method = Method.listStreams.name();

    Params params;

    public ListStreamsRequest(String token, Integer listLimit, Integer listOffset, String sortMode){
        params=new Params(token, listLimit, listOffset, sortMode);
    }

    @Data
    @NoArgsConstructor
    public class Params{

        String token;
        String streamType="all";
        Integer listLimit;
        Integer listOffset;
        String listFilter="";
        String sortMode="date";
        String imageSize="big";

        public Params(String token, Integer listLimit, Integer listOffset, String sortMode){
            this.token=token;
            this.listLimit=listLimit;
            this.listOffset=listOffset;
            this.sortMode=sortMode;
        }

    }

}

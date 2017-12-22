package com.example.demo.transport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pavlovskii-pc on 22/12/2017.
 */
@Data
@AllArgsConstructor
public class ScheduleStreamRequest extends BaseRequest {

    String method=Method.scheduleStream.name();

    Params params;

    public ScheduleStreamRequest(String streamId, String token){
        params=new Params(streamId, token);
    }

    @Data
    @NoArgsConstructor
    public class Params {
        String token;
        String streamId;
        String identifier;
        String clientIp="";
        String deviceId="";
        String resolution="1600x900";
        String domain;
        String protocol="https";
        String referrer="";

        public Params(String streamId, String token){
            this.token=token;
            this.streamId=streamId;
        }
    }

}

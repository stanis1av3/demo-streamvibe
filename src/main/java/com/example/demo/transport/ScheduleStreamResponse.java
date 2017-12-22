package com.example.demo.transport;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pavlovskii-pc on 22/12/2017.
 */
@Data
@NoArgsConstructor
public class ScheduleStreamResponse extends BaseResponse {

    String status;
    Result result;

    @Data
    @NoArgsConstructor
    public class Result {
        String url;
        Integer options;
        Integer viewMode;
        String timeStamp;
    }

}

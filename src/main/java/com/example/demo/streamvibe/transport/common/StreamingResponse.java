package com.example.demo.streamvibe.transport.common;

import com.example.demo.streamvibe.transport.integration.BaseResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by stas on 12/23/2017.
 */
@Data
@NoArgsConstructor
public class StreamingResponse extends BaseResponse{

    String streamId;
    String videoUrl;

    public StreamingResponse(String streamId, String videoUrl){
        setStreamId(streamId);
        setVideoUrl(videoUrl);
        setStatus(Status.OK.name());
    }

}

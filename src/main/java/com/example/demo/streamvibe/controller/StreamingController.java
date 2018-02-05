package com.example.demo.streamvibe.controller;

import com.example.demo.streamvibe.service.StreamingService;
import com.example.demo.streamvibe.transport.common.StreamingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by stas on 12/23/2017.
 */

@Controller
public class StreamingController {

    @Autowired
    StreamingService streamingService;



    @RequestMapping(value = "/video/upload",
            method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadVideo(@RequestParam(value = "file", required = true) MultipartFile file) throws Exception {


        String streamId = streamingService.uploadVideoStream(file.getBytes());
        String streamUrl = streamingService.getVideoStreamUrl(streamId);

        return new ResponseEntity(new StreamingResponse(streamId, streamUrl), HttpStatus.OK);

    }

    @RequestMapping(value = "/video/{streamId}",
            produces = APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity getVideoItem(@PathVariable String streamId) {
        return new ResponseEntity<>(new StreamingResponse(streamId, streamingService.getStreamReadyUrl(streamId)), HttpStatus.OK);
    }

    @RequestMapping(value = "/video",
            produces = APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity getVideoItems(@RequestParam Integer listLimit, @RequestParam Integer listOffset, @RequestParam(required = false, defaultValue = "date") String sortMode) {
        return new ResponseEntity<>(streamingService.listVideoStreams(listLimit, listOffset, sortMode), HttpStatus.OK);
    }

    @RequestMapping(value = "/video/{streamId}",
            produces = APPLICATION_JSON_VALUE,
            method = RequestMethod.DELETE)
    public ResponseEntity deleteVideoItem(@PathVariable String streamId) {
        return new ResponseEntity<>(streamingService.unregisterVideoStream(streamId), HttpStatus.OK);
    }
}

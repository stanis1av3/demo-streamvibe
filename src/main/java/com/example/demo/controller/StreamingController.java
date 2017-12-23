package com.example.demo.controller;

import com.example.demo.service.StreamingService;
import com.example.demo.service.StreamingServiceImpl;
import com.example.demo.transport.common.StreamingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by stas on 12/23/2017.
 */

@Controller
public class StreamingController {

    @Autowired
    StreamingService streamingService;

    @RequestMapping(value = "/video/upload",
            method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadPhoto(@RequestParam(value = "file", required = true) MultipartFile file) throws Exception {


        String streamId = streamingService.uploadVideoStream(file.getBytes());
        String streamUrl = streamingService.getVideoStreamUrl(streamId);

        return new ResponseEntity(new StreamingResponse(streamId, streamUrl), HttpStatus.OK);

    }

}

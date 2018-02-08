package com.example.demo.ussd.controller;

import com.example.demo.ussd.model.UssdMessageDTO;
import com.example.demo.ussd.service.UssdRootService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Controller
public class UssdController {

    @Autowired
    UssdRootService ussdService;

    @RequestMapping(value = "/ussd/gateway",
            method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadVideo(@RequestBody UssdMessageDTO messageDTO) throws Exception {


        return new ResponseEntity(ussdService.renderResponse(messageDTO), HttpStatus.OK);

    }

    //return new ResponseEntity(messageDTO, HttpStatus.OK);

    @RequestMapping(value = "/ussd/emulator",
            method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity emulator() throws Exception {

        Resource resource = new ClassPathResource("ussd-tester.html");
        InputStream is = resource.getInputStream();
        String result = new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining("\n"));


        return new ResponseEntity(result, HttpStatus.OK);


    }

}

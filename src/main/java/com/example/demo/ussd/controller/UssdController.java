package com.example.demo.ussd.controller;

import com.example.demo.ussd.model.UssdMessageDTO;
import com.example.demo.ussd.service.UssdRootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

        //todo: te

        return new ResponseEntity(ussdService.renderResponse(messageDTO), HttpStatus.OK);


        //return new ResponseEntity(messageDTO, HttpStatus.OK);


    }

}

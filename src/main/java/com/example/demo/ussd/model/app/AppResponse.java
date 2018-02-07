package com.example.demo.ussd.model.app;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by Pavlovskii-pc on 06/02/2018.
 */
@Data
@AllArgsConstructor

public class AppResponse {

    List<String> body;
    Integer offset = 0;

    public AppResponse(List<String> body){
        this.body=body;
    }
    public AppResponse(String body){
        this.body = Lists.newArrayList(body);
    }

    public AppResponse(Integer offset){
        this.offset = offset;
    }



}

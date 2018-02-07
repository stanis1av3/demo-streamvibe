package com.example.demo.ussd.model.app;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by Pavlovskii-pc on 06/02/2018.
 */
@Data
@AllArgsConstructor

public class AppViewObject {

    List<String> body;
    Integer offset = 0;

    public AppViewObject(List<String> body){
        this.body=body;
    }

    public AppViewObject(Integer offset){
        this.offset = offset;
    }



}

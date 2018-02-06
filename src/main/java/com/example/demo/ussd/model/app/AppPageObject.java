package com.example.demo.ussd.model.app;

import lombok.Data;

import java.util.List;

/**
 * Created by Pavlovskii-pc on 06/02/2018.
 */
@Data

public class AppPageObject {

    String output;
    List<String> body;
    String controls;

}

package com.example.demo.ussd.apps.custom.license;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppResponse;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Pavlovskii-pc on 08/02/2018.
 */
@Component("ABOUT_SECO_APP")
public class AboutSecoApp implements UssdApp {
    @Override
    public AppResponse run(String from, String input) {
        String header = "About seco:";
        String body = "About SECO text";
        String footer = "<<0";

        if(input.equals("0")){
            return new AppResponse(new ArrayList<>(), -1);
        }

        return new AppResponse(Lists.newArrayList(header, body, footer));
    }
}

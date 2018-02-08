package com.example.demo.ussd.apps.custom.about;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppResponse;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Pavlovskii-pc on 08/02/2018.
 */
@Component("ABOUT_US_APP")
public class AboutUsApp implements UssdApp {
    @Override
    public AppResponse run(String from, String input) {
        String header = "About title:";
        String body = "About marketing text placeholder";
        String footer = "<<0";

        if(input.equals("0")){
            return new AppResponse(new ArrayList<>(), -1);
        }

        return new AppResponse(Lists.newArrayList(header, body, footer));
    }

    @Override
    public UssdApp init(String from) {
        return this;
    }

    @Override
    public UssdApp destroy(String from) {
        return this;
    }
}

package com.example.demo.ussd.apps.custom.events;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppResponse;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Pavlovskii-pc on 08/02/2018.
 */
@Component("EVENTS_APP")
public class AboutAddressApp implements UssdApp {
    @Override
    public AppResponse run(String from, String input) {
        String header = "Under construction:";
        String footer = "<<0";

        if(input.equals("0")){
            return new AppResponse(new ArrayList<>(), -1);
        }

        return new AppResponse(Lists.newArrayList(header, footer));
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

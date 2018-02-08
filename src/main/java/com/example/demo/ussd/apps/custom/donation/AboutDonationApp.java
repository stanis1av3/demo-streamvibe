package com.example.demo.ussd.apps.custom.donation;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppResponse;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Pavlovskii-pc on 08/02/2018.
 */
@Component("ABOUT_DONATION_APP")
public class AboutDonationApp implements UssdApp {
    @Override
    public AppResponse run(String from, String input) {
        String header = "About:";
        String body = "Your donation is important for us!";
        String body2 = "We highly appreciate it!";
        String footer = "<<0";

        if(input.equals("0")){
            return new AppResponse(new ArrayList<>(), -1);
        }

        return new AppResponse(Lists.newArrayList(header, body, body2, footer));
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

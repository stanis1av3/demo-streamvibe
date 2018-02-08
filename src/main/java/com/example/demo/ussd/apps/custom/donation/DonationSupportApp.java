package com.example.demo.ussd.apps.custom.donation;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppResponse;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Pavlovskii-pc on 08/02/2018.
 */
@Component("DONATION_SUPPORT_APP")
public class DonationSupportApp implements UssdApp{
    @Override
    public AppResponse run(String from, String input) {
        String header = "Contact us:";
        String body = "(+225) 20225209";
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

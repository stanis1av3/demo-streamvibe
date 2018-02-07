package com.example.demo.ussd.apps.custom;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppResponse;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("EXCHANGE_RATES_APP")
public class ExchangeRatesApp implements UssdApp {


    String string;

    @Override
    public AppResponse run(String from, String input) {

        System.out.println(string);
        if (!input.equals("0")) {
            return new AppResponse(Lists.newArrayList("USD:22.41", "EUR:25.11", "EUR/USD:1.25", "0 back"));
        } else {
           return new AppResponse(new ArrayList<>(), -1);
        }
    }

    @Override
    public UssdApp init(String from) {
        this.string=from;
        return this;
    }

    @Override
    public UssdApp destroy(String from) {
        return this;
    }


}
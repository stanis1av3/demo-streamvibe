package com.example.demo.ussd.apps;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppViewObject;
import com.example.demo.ussd.service.UssdSessionService;
import com.example.demo.ussd.util.UssdAppUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("EXCHANGE_RATES_APP")
public class ExchangeRatesApp implements UssdApp {


    @Override
    public AppViewObject run(String from, String input) {

        if (!input.equals("0")) {
            return new AppViewObject(Lists.newArrayList("USD:22.41", "EUR:25.11", "EUR/USD:1.25", "0 back"));
        } else {
           return new AppViewObject(new ArrayList<>(), -1);
        }
    }


}
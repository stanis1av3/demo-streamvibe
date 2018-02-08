package com.example.demo.ussd.apps.custom.license;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppResponse;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

/**
 * Created by Pavlovskii-pc on 08/02/2018.
 */
@Component("LICENSE_PRICE_PRIMA_APP")
public class LicencePricePrimaApp implements UssdApp{
    @Override
    public AppResponse run(String from, String input) {
        String header = "Licence price:";
        String body = "Licence price: $1000";
        String footer = "<<0";

        if(input.equals("0")){
            return AppResponse.goBackAfter();
        }

        return AppResponse.stayHere(Lists.newArrayList(header, body, footer));
    }
}

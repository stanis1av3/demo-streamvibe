package com.example.demo.ussd.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Service
public class UssdAppsService {

    public List<String> currencyExchangeRate(){
        return Lists.newArrayList("USD:22.41", "EUR:25.11", "EUR/USD:1.25", "0 back");
    }
    public List<String> economicsNews(){
        return Lists.newArrayList("US Dollar drops down desperately!! Euro is the best!", "0 back");
    }
}

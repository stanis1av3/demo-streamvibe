package com.example.demo.ussd.service;

import com.example.demo.ussd.model.app.Item;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Service
public class UssdAppsService {

    @Autowired
    UssdSessionService sessionService;

    public String goBackApp(String from){
        Item item = sessionService.goBack(from);

        return item.getParent().getChildItems().stream().map(i->i.getCaption()).reduce((a,b)->a+"\n"+b).get();

    }

    public String menuApp(String from){
        Item item = sessionService.goBack(from);
        return item.getChildItems().stream().map(i->i.getCaption()).reduce((a,b)->a+"\n"+b).get();
    }

    public List<String> currencyExchangeRate(){
        return Lists.newArrayList("USD:22.41", "EUR:25.11", "EUR/USD:1.25", "0 back");
    }
    public List<String> economicsNews(){
        return Lists.newArrayList("US Dollar drops down desperately!! Euro is the best!", "0 back");
    }
}

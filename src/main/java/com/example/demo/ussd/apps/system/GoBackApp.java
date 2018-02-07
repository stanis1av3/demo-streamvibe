package com.example.demo.ussd.apps.system;

import com.example.demo.ussd.model.app.AppItem;
import com.example.demo.ussd.service.UssdSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("GO_BACK_APP")
public class GoBackApp implements UssdApp {

    @Autowired
    UssdSessionService sessionService;

    public List<String> run(String from, String input) {

        sessionService.goBack(from);
        sessionService.goBack(from);
        AppItem item = sessionService.getCurrent(from);

        return item.getChildItems()
                .stream()
                .map(i -> i.getCaption()).collect(Collectors.toList());


    }


}
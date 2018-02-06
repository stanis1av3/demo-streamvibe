package com.example.demo.ussd.service;

import com.example.demo.ussd.model.app.AppItem;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Service
public class UssdAppsService {

    @Autowired
    UssdSessionService sessionService;

    public List<String> goBackApp(String from) {
        AppItem item = sessionService.goBack(from);

        return item.getParent().getChildItems()
                .stream()
                .map(i -> i.getCaption()).collect(Collectors.toList());


    }

    public List<String> menuApp(String from, String input) {
        AppItem item = sessionService.getCurrent(from);

        try {
            Integer index = Integer.valueOf(input);
            AppItem nextItem = sessionService.goForward(from, input);
            return nextItem.getChildItems().get(index - 1).getChildItems()
                    .stream()
                    .map(AppItem::getCaption).collect(Collectors.toList());
        } catch (Exception e) {
            return item.getChildItems()
                    .stream()
                    .map(AppItem::getCaption).collect(Collectors.toList());
        }

    }

    public List<String> currencyExchangeRate() {
        return Lists.newArrayList("USD:22.41", "EUR:25.11", "EUR/USD:1.25", "0 back");
    }

    public List<String> economicsNews(String from, String input) {
        if (!input.equals("0")) {
            return Lists.newArrayList("US Dollar drops down desperately!! Euro is the best!", "0 back");
        } else {
            AppItem item = sessionService.goBack(from);
            return item.getParent().getChildItems()
                    .stream()
                    .map(i -> i.getCaption()).collect(Collectors.toList());
        }

    }
}

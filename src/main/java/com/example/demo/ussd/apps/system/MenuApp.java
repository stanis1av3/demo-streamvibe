package com.example.demo.ussd.apps.system;

import com.example.demo.ussd.model.app.AppItem;
import com.example.demo.ussd.model.app.AppViewObject;
import com.example.demo.ussd.repository.UssdItemRepository;
import com.example.demo.ussd.service.UssdSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("MENU")
public class MenuApp implements UssdApp {

    @Autowired
    UssdSessionService sessionService;

    @Autowired
    UssdItemRepository itemRepository;

    @Override
    public AppViewObject run(String from, String input) {
        AppItem item = sessionService.getCurrent(from);

        if(input.equals("")) {
            try {
                return new AppViewObject(item.getChildItems().stream().map(i->i.getCaption()).collect(Collectors.toList()));

            } catch (Exception e) {
                return new AppViewObject(itemRepository.getRootItem().getChildItems()
                        .stream()
                        .map(AppItem::getCaption).collect(Collectors.toList()));
            }
        };
        if(input.equals("0")){
            return new AppViewObject(-1);
        }

        Integer index = parseInput(input);

        if(index!=null){
            return new AppViewObject(index);
        }

        return new AppViewObject(itemRepository.getRootItem().getChildItems()
                .stream()
                .map(AppItem::getCaption).collect(Collectors.toList()));
    }

    private Integer parseInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return null;
        }
    }


}
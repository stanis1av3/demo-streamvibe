package com.example.demo.ussd.apps.system;

import com.example.demo.ussd.model.app.AppItem;
import com.example.demo.ussd.repository.UssdItemRepository;
import com.example.demo.ussd.service.UssdSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("MENU")
public class MenuApp implements UssdApp {

    @Autowired
    UssdSessionService sessionService;

    @Autowired
    UssdItemRepository itemRepository;

    @Override
    public List<String> run(String from, String input) {
        AppItem item = sessionService.getCurrent(from);

        try {
            return item.getChildItems()
                    .stream()
                    .map(AppItem::getCaption).collect(Collectors.toList());
        } catch (Exception e) {
            return itemRepository.getRootItem().getChildItems()
                    .stream()
                    .map(AppItem::getCaption).collect(Collectors.toList());
        }
    }

}
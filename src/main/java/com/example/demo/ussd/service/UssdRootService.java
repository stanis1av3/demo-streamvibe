package com.example.demo.ussd.service;

import com.example.demo.ussd.model.UssdMessageDTO;
import com.example.demo.ussd.model.app.Item;
import com.example.demo.ussd.repository.UssdItemRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Service
public class UssdRootService {

    @Autowired
    UssdAppsService appsService;

    @Autowired
    UssdSessionService sessionService;

    Item root;

    @Autowired
    UssdItemRepository itemRepository;



    public UssdMessageDTO renderResponse(UssdMessageDTO messageDTO) {

        Item item = sessionService.getCurrent(messageDTO.getFrom());

        String inputMessage = messageDTO.getMessage();
        String from = messageDTO.getFrom();

        if (item.getType()==Item.Type.MENU) {
            item = sessionService.goForward(from, inputMessage);
        }

        if (item.getType() == Item.Type.APP_GO_BACK) {
            item = sessionService.goBack(messageDTO.getFrom());
        }

        if(item.getType() == Item.Type.APP_EXCHANGE_RATES) {

        }


        String preparedView = item.getChildItems()
                .stream()
                .map(i -> i.getCaption()).reduce((a, b) -> a + "\n" + b)
                .get();


        UssdMessageDTO responseMessageDTO = new UssdMessageDTO();
        responseMessageDTO.setTo(messageDTO.getFrom());
        responseMessageDTO.setMessage(preparedView);
        return responseMessageDTO;
    }



    private Integer convertStringMessageToInteger(String userMessage) {
        try {
            return Integer.parseInt(userMessage);
        } catch (Exception e) {
            return null;
        }
    }

    @PostConstruct
    void started(){
        root = itemRepository.getRootItem();
    }
}

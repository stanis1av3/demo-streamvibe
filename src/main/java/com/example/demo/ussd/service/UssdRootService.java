package com.example.demo.ussd.service;

import com.example.demo.ussd.model.UssdMessageDTO;
import com.example.demo.ussd.model.app.Item;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Service
public class UssdRootService {

    @Autowired
    UssdAppsService appsService;

    Item root = new Item();

    public Map<String, ArrayList<String>> sessions = new HashMap<>();


    public UssdMessageDTO renderResponse(UssdMessageDTO messageDTO) {


        List<String> userMessages;

        if (!messageDTO.getMessage().equals("0")) {
            userMessages = goForward(messageDTO.getFrom(), messageDTO.getMessage());
        } else {
            userMessages = goBack(messageDTO.getFrom());
        }

        List<String> views = processMessages(userMessages);

        String preparedView = views.stream().reduce((a, b) -> a + "\n" + b).get();

        UssdMessageDTO responseMessageDTO = new UssdMessageDTO();
        responseMessageDTO.setTo(messageDTO.getFrom());
        responseMessageDTO.setMessage(preparedView);
        return responseMessageDTO;
    }

    private List<String> goForward(String from, String message) {
        if (sessions.containsKey(from)) {
            sessions.get(from).add(message);
            return sessions.get(from);
        } else {
            sessions.put(from, Lists.newArrayList(message));
            return sessions.get(from);
        }
    }

    private List<String> goBack(String from) {
        ArrayList<String> messages = sessions.get(from);
        messages.remove(messages.size() - 1);
        sessions.put(from, messages);
        return messages;
    }

    private List<String> processMessages(List<String> userMessages) {
        List<String> responseViews = new ArrayList<>();

        Item item = root;

        for (String userMessage : userMessages) {

            Integer userInput = convertStringMessageToInteger(userMessage);

            if (userInput != null) {
                item = item.getChildItems().get(convertStringMessageToInteger(userMessage) - 1);
            }

            if (item.getType() == Item.Type.MENU) {
                responseViews = item.getChildItems().stream().map(i -> i.getCaption()).collect(Collectors.toList());
            }

            if (item.getType() == Item.Type.APP_EXCHANGE_RATES) {
                responseViews = appsService.currencyExchangeRate();
            }

            if(item.getType() == Item.Type.APP_ECONOMICS_NEWS) {
                responseViews = appsService.economicsNews();
            }


        }

        return responseViews;
    }

    private Integer convertStringMessageToInteger(String userMessage) {
        try {
            return Integer.parseInt(userMessage);
        } catch (Exception e) {
            return null;
        }
    }

    @PostConstruct
    public void started() {

        root.setType(Item.Type.MENU);

        Item itemA = new Item();
        itemA.setCaption("1 Currencies");
        itemA.setType(Item.Type.APP_EXCHANGE_RATES);

        Item itemB = new Item();
        itemB.setCaption("2 News");
        itemB.setType(Item.Type.MENU);

        Item itemC = new Item();
        itemC.setCaption("3 Weather");
        itemC.setType(Item.Type.MENU);

        Item itemQuit = new Item();

        itemQuit.setCaption("0 Quit");
        itemQuit.setType(Item.Type.MENU);

        root.setChildItems(Lists.newArrayList(itemA, itemB, itemC, itemQuit));

        Item back = new Item();
        back.setCaption("0 back");
        back.setType(Item.Type.MENU);

        Item itemBA = new Item();
        itemBA.setCaption("1 Local news");
        itemBA.setType(Item.Type.MENU);

        Item itemBB = new Item();
        itemBB.setCaption("2 Federal news");
        itemBB.setType(Item.Type.MENU);
        itemB.setChildItems(Lists.newArrayList(itemBA, itemBB, back));

        Item itemBBA = new Item();
        itemBBA.setCaption("1 Economics");
        itemBBA.setType(Item.Type.APP_ECONOMICS_NEWS);


        Item itemBBB = new Item();
        itemBBB.setCaption("2 Security");
        itemBBB.setType(Item.Type.APP_A);

        Item itemBBC = new Item();
        itemBBC.setCaption("3 Culture");
        itemBBC.setType(Item.Type.APP_B);

        Item itemBBD = new Item();
        itemBBD.setCaption("4 Sports");
        itemBBD.setType(Item.Type.APP_C);

        itemBB.setChildItems(Lists.newArrayList(itemBBA, itemBBB, itemBBC, itemBBD, back));

        root.setCaption("hello!");

    }

}

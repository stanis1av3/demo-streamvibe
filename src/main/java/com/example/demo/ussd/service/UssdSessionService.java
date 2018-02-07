package com.example.demo.ussd.service;

import com.example.demo.ussd.apps.custom.StateObject;
import com.example.demo.ussd.model.app.AppItem;
import com.example.demo.ussd.repository.UssdItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Service
public class UssdSessionService {


    @Autowired
    UssdItemRepository itemRepository;

    public Map<String, AppItem> itemSesstions = new HashMap<>();

    AppItem root;

    public Map<String, StateObject> appSession = new HashMap<>();

    public AppItem goForward(String from, String message) {

        if(StringUtils.isEmpty(message)){
            return getCurrent(from);
        }

        if (itemSesstions.containsKey(from)) {
            AppItem nextItem;
            AppItem currentItem = itemSesstions.get(from);
            if (itemSesstions.get(from).getChildItems() != null) {
                nextItem = itemSesstions.get(from).getChildItems().stream().filter(i->i.getCaption().substring(0,1).equals(message)).findFirst().get();
                itemSesstions.put(from, nextItem);
            } else {
                return root;
            }
            nextItem.setParent(currentItem);
            return nextItem;

        } else {
            return itemSesstions.put(from, root);
        }
    }

    public AppItem goBack(String from) {
        if (itemSesstions.containsKey(from)) {
            AppItem item = itemSesstions.get(from);
            itemSesstions.put(from, item.getParent() != null ? item.getParent() : root);
            return itemSesstions.get(from);
        } else {
            return itemSesstions.put(from, root);
        }

    }

    public AppItem getCurrent(String from){
        if (itemSesstions.containsKey(from)) {
            return itemSesstions.get(from);
        } else {
            itemSesstions.put(from, root);
            return root;
        }
    }

    public StateObject persistObject(String from, StateObject state) {
        appSession.put(from, state);
        return state;
    }

    public StateObject getObject(String from){
        return appSession.get(from);
    }


    @PostConstruct
    void started(){
        root=itemRepository.getRootItem();
    }
}

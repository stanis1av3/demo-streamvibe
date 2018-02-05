package com.example.demo.ussd.service;

import com.example.demo.ussd.model.app.Item;
import com.example.demo.ussd.repository.UssdItemRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Service
public class UssdSessionService {


    @Autowired
    UssdItemRepository itemRepository;

    public Map<String, ArrayList<String>> userSessions = new HashMap<>();
    public Map<String, Item> itemSesstions = new HashMap<>();

    Item root;



    public Item goForward(String from, String message) {

        if(StringUtils.isEmpty(message)){
            return getCurrent(from);
        }

        if (itemSesstions.containsKey(from)) {
            Item nextItem;
            if (itemSesstions.get(from).getChildItems() != null) {
                nextItem = itemSesstions.get(from).getChildItems().get(Integer.valueOf(message)-1);
            } else {
                return root;
            }
            nextItem.setParent(itemSesstions.get(from));
            return nextItem;

        } else {
            return itemSesstions.put(from, root);
        }
    }

    public Item goBack(String from) {
        if (itemSesstions.containsKey(from)) {
            Item item = itemSesstions.get(from);
            return item.getParent() != null ? item.getParent() : root;
        } else {
            return itemSesstions.put(from, root);
        }

    }

    public Item getCurrent(String from){
        if (itemSesstions.containsKey(from)) {
            return itemSesstions.get(from);
        } else {
            itemSesstions.put(from, root);
            return root;
        }
    }


    @PostConstruct
    void started(){
        root=itemRepository.getRootItem();
    }
}

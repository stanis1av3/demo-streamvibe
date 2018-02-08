package com.example.demo.ussd.service;

import com.example.demo.ussd.util.MapperUtils;
import com.example.demo.ussd.util.StateObject;
import com.example.demo.ussd.model.app.AppItem;
import com.example.demo.ussd.repository.UssdItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Service
public class UssdSessionService {

    @Autowired
    StringRedisTemplate redisTemplate;


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

    public StateObject persistSessionObject(String from, StateObject state) {
        appSession.put(from, state);
        return state;
    }

    public StateObject getSessionObject(String from){
        return appSession.get(from);
    }


    private AppItem save(String key, AppItem appItem){
        redisTemplate.opsForValue().set("app_item"+key, MapperUtils.toJson(appItem));
        return appItem;
    }

    private StateObject save(String key, StateObject stateObject){
        redisTemplate.opsForValue().set("state_object"+key, MapperUtils.toJson(stateObject));
        return stateObject;
    }

    private StateObject getStateObject(String key){
        String json = redisTemplate.opsForValue().get("state_object"+key);
        return MapperUtils.toObject(StateObject.class, json);
    }


    private AppItem getAppItem(String key){
        String json = redisTemplate.opsForValue().get("app_item"+key);
        return MapperUtils.toObject(AppItem.class, json);
    }


    @PostConstruct
    void started(){
        root=itemRepository.getRootItem();
        redisTemplate.opsForValue().set("testkey","testvalue");
        System.out.println(redisTemplate.opsForValue().get("testkey"));
    }
}

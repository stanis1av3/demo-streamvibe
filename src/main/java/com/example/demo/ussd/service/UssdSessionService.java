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

        if (StringUtils.isEmpty(message)) {
            return getCurrent(from);
        }

        if (getAppItem(from) != null) {
            AppItem nextItem;
            AppItem currentItem = getAppItem(from);
            if (getAppItem(from).getChildItems() != null) {
                nextItem = getAppItem(from).getChildItems().stream().filter(i -> i.getCaption().substring(0, 1).equals(message)).findFirst().get();
                save(from, nextItem);
            } else {
                return root;
            }
            nextItem.setParent(currentItem);
            return nextItem;

        } else {
            return save(from, root);
        }
    }

    public AppItem goBack(String from) {
        if (getAppItem(from) != null) {
            AppItem item = getAppItem(from);
            save(from, item.getParent() != null ? item.getParent() : root);
            return getAppItem(from);
        } else {
            return save(from, root);
        }

    }

    public AppItem getCurrent(String from) {
        if (getAppItem(from) != null) {
            return getAppItem(from);
        } else {
            save(from, root);
            return root;
        }
    }

    public StateObject persistSessionObject(String from, StateObject state) {
        save(from, state);
        return state;
    }

    public StateObject getSessionObject(String from) {
        return getStateObject(from);
    }


    private AppItem save(String key, AppItem appItem) {
        redisTemplate.opsForValue().set("app_item" + key, MapperUtils.toJson(appItem));
        return appItem;
    }

    private StateObject save(String key, StateObject stateObject) {
        redisTemplate.opsForValue().set("state_object" + key, MapperUtils.toJson(stateObject));
        return stateObject;
    }

    private StateObject getStateObject(String key) {
        String json = redisTemplate.opsForValue().get("state_object" + key);
        return MapperUtils.toObject(StateObject.class, json);
    }


    private AppItem getAppItem(String key) {
        String json = redisTemplate.opsForValue().get("app_item" + key);
        if (json != null)
            return MapperUtils.toObject(AppItem.class, json);
        else
            return null;
    }


    @PostConstruct
    void started() {
        root = itemRepository.getRootItem();
        redisTemplate.opsForValue().set("testkey", "testvalue");
        System.out.println(redisTemplate.opsForValue().get("testkey"));
    }
}

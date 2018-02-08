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

        AppItem currentItem = getAppItem(from);
        if (currentItem != null) {
            AppItem nextItem;

            if (currentItem.getChildItems() != null) {
                nextItem = currentItem.getChildItems().stream().filter(i -> i.getCaption().substring(0, 1).equals(message)).findFirst().get();

            } else {
                return root;
            }
            //nextItem.setParent(currentItem);
            save(from, nextItem);
            return nextItem;

        } else {
            return save(from, root);
        }
    }

    public AppItem goBack(String from) {
        AppItem item = getAppItem(from);
        if (item != null) {
            AppItem parent = itemRepository.findParent(item);
            save(from, parent != null ? parent : root);
            return getAppItem(from);
        } else {
            return save(from, root);
        }

    }

    public AppItem getCurrent(String from) {
        AppItem current = getAppItem(from);
        if (current != null) {
            return current;
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
        if (json != null)
            return MapperUtils.toObject(StateObject.class, json);
        else return null;
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
    }
}

package com.example.demo.ussd.util;

import com.example.demo.ussd.model.app.AppItem;

import java.util.List;
import java.util.stream.Collectors;

public class UssdAppUtil {

    public static List<String> quitApp(AppItem appItem){
        return appItem.getChildItems().stream().map(i->i.getCaption()).collect(Collectors.toList());
    }
}
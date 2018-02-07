package com.example.demo.ussd.apps;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.service.UssdSessionService;
import com.example.demo.ussd.util.UssdAppUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component("ECONOMICS_NEWS_APP")
public class EconomicsNewsApp implements UssdApp {

    @Autowired
    UssdSessionService sessionService;


    @Override
    public List<String> run(String from, String input) {
        List<String> news = Lists.newArrayList("1 item one", "2 item two", "3 item three", "0 quit");

        if(StringUtils.isEmpty(input)){
            return news;
        }

        if(input.equals("0")){
            return Lists.newArrayList(UssdAppUtil.quitApp(sessionService.goBack(from)));

        }

        if(parseInteger(input)!=null) {
            return Lists.newArrayList(news.get(parseInteger(input)-1));
        }

        return news;

    }

    private Integer parseInteger(String input){
        try{
            return Integer.parseInt(input);
        }catch (Exception e){
            return null;
        }
    }
}
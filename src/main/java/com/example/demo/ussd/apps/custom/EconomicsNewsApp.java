package com.example.demo.ussd.apps.custom;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppResponse;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component("ECONOMICS_NEWS_APP")
public class EconomicsNewsApp implements UssdApp {


    @Override
    public AppResponse run(String from, String input) {
        List<String> news = Lists.newArrayList("1 item one", "2 item two", "3 item three", "0 quit");

        if(StringUtils.isEmpty(input)){
            return new AppResponse(news);
        }
        if(input.equals("0")){
            return new AppResponse(-1);

        }
        Integer inputIndex = parseInteger(input);
        if(inputIndex !=null) {
            if(inputIndex==2){
                String a = "I found a solution. I am not very happy with it since it still does not answer my original question why the logging.file property is not respected. ... I don't know whether this would help you but I  ";

                return new AppResponse(Lists.newArrayList(a));
            }
            return new AppResponse(Lists.newArrayList(news.get(parseInteger(input)-1)));
        }
        return new AppResponse(news);

    }

    private Integer parseInteger(String input){
        try{
            return Integer.parseInt(input);
        }catch (Exception e){
            return null;
        }
    }
}
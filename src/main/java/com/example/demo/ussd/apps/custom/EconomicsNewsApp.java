package com.example.demo.ussd.apps.custom;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppResponse;
import com.example.demo.ussd.service.UssdSessionService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component("ECONOMICS_NEWS_APP")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EconomicsNewsApp implements UssdApp {

    @Autowired
    UssdSessionService sessionService;

    PageObject stateObject;

    @Override
    public AppResponse run(String from, String input) {

        stateObject = sessionService.getObject(from);

        if (stateObject != null) {
            if (input.equals("7")) {
                stateObject.

            }

        }
        List<String> news = Lists.newArrayList("1 item one", "2 item two", "3 item three", "0 quit");

        String item2 = "I found a solution. I am not very happy with it since it still does not answer my original question why the logging.file property is not respected. ... I don't know whether this would help you but I  ";

        if (StringUtils.isEmpty(input)) {
            return new AppResponse(news);
        }

        if (input.equals("0")) {
            sessionService.persistObject(from, null);
            return new AppResponse(-1);

        }


        Integer inputIndex = parseInteger(input);

        return new AppResponse(news);

    }

    private Integer parseInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return null;
        }
    }


}
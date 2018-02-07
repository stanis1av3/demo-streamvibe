package com.example.demo.ussd.apps.custom;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppResponse;
import com.example.demo.ussd.service.UssdSessionService;
import com.example.demo.ussd.util.SessionObject;
import com.example.demo.ussd.util.StringPageObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component("ECONOMICS_NEWS_APP")
public class EconomicsNewsApp implements UssdApp {

    @Autowired
    UssdSessionService sessionService;

    private SessionObject sessionObject;

    List<String> news;
    String item2 = "I found a solution. I am not very happy with it since it still does not answer my original question why the logging.file property is not respected. ... I don't know whether this would help you but I  ";

    @Override
    public AppResponse run(String from, String input) {

        news = Lists.newArrayList("1 item one", "2 item two", "3 item three", "0 quit");

        //on input 0
        if (input.equals("0")) {
            return onInput0(from);
        }

        //on input 2
        if (input.equals("2")) {
            return onInput2(from);
        }

        //on input 9
        if (input.equals("9")) {
            return onInput9(from);
        }

        //on input 7
        if (input.equals("7")) {
            return onInput7(from);
        }

        return onInputAny(from);

    }

    AppResponse onInput0(String from) {
        if (sessionObject.getPageObject() == null) {
            return new AppResponse(-1);
        } else {
            //clear pageObject
            sessionObject.setPageObject(null);
            sessionObject = sessionService.persistSessionObject(from, sessionObject);
            return new AppResponse(news);
        }
    }

    AppResponse onInput2(String from) {
        sessionObject.setPageObject(new StringPageObject(item2));
        sessionObject = sessionService.persistSessionObject(from, sessionObject);
        return new AppResponse(sessionObject.getPageObject().getCurrent());

    }

    AppResponse onInput7(String from) {
        return new AppResponse(sessionObject.getPageObject().getPrev());
    }

    AppResponse onInput9(String from) {
        return new AppResponse(sessionObject.getPageObject().getNext());
    }

    AppResponse onInputAny(String from) {
        return new AppResponse(news);
    }


    @Override
    public UssdApp init(String from) {
        if (sessionService.getSessionObject(from) == null) {
            sessionService.persistSessionObject(from, new SessionObject());
        }
        this.sessionObject = sessionService.getSessionObject(from);
        return this;
    }

    @Override
    public UssdApp destroy(String from) {
        sessionService.persistSessionObject(from, null);
        return this;
    }

    private Integer parseInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return null;
        }
    }


}
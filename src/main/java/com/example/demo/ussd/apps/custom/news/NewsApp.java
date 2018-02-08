package com.example.demo.ussd.apps.custom.news;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.app.AppResponse;
import com.example.demo.ussd.service.UssdSessionService;
import com.example.demo.ussd.util.ListPageObject;
import com.example.demo.ussd.util.StateObject;
import com.example.demo.ussd.util.StringPageObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("NEWS_APP")
public class NewsApp implements UssdApp {
//todo:a stub
    @Autowired
    UssdSessionService sessionService;

    private StateObject stateObject;

    List<String> bigNewsList;
    String item2 = "I found a solution. I am not very happy with it since it still does not answer my original question why the logging.file property is not respected. ... I don't know whether this would help you but I  ";

    @Override
    public AppResponse run(String from, String input) {

        bigNewsList = Lists.newArrayList("item one", "item two", "item three", "item four", "item five", "item item six", "item 7", "item 8", "item 9", "item 10");

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
//        if (stateObject.getPageObject() == null && stateObject.getListPageObject() == null) {
//            return new AppResponse(-1);
//        }

        if(stateObject.getPageObject()!=null)
        {
            //clear pageObject
            stateObject.setPageObject(null);
            stateObject = sessionService.persistSessionObject(from, stateObject);
            return new AppResponse(stateObject.getListPageObject().getCurrent());
        }
        if(stateObject.getListPageObject()!=null){
            stateObject.setListPageObject(null);
            stateObject = sessionService.persistSessionObject(from, stateObject);
        }
        return new AppResponse(-1);
    }

    AppResponse onInput2(String from) {

        stateObject.setPageObject(new StringPageObject(item2));
        AppResponse appResponse = new AppResponse(stateObject.getPageObject().getCurrent());
        sessionService.persistSessionObject(from, stateObject);
        return appResponse;

    }

    AppResponse onInput7(String from) {
        AppResponse appResponse = new AppResponse(stateObject.getPageObject().getPrev());;
        sessionService.persistSessionObject(from, stateObject);
        return appResponse;
    }

    AppResponse onInput9(String from) {
        AppResponse appResponse = new AppResponse(stateObject.getPageObject().getNext());
        sessionService.persistSessionObject(from, stateObject);
        return appResponse;
    }

    AppResponse onInputAny(String from) {
        stateObject.setListPageObject(new ListPageObject(bigNewsList));
        stateObject = sessionService.persistSessionObject(from, stateObject);
        return new AppResponse(stateObject.getListPageObject().getCurrent());
    }


    @Override
    public UssdApp init(String from) {
        if (sessionService.getSessionObject(from) == null) {
            sessionService.persistSessionObject(from, new StateObject());
        }
        this.stateObject = sessionService.getSessionObject(from);
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
package com.example.demo.ussd.apps.system;

import com.example.demo.ussd.model.app.AppItem;
import com.example.demo.ussd.model.app.AppViewObject;

import java.util.List;

public interface UssdApp {
    AppViewObject run(String from, String input);
}
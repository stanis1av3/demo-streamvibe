package com.example.demo.ussd.apps.system;

import com.example.demo.ussd.model.app.AppResponse;

public interface UssdApp {
    AppResponse run(String from, String input);
    default UssdApp init(String from){return this;}
    default UssdApp destroy(String from){return this;}
}
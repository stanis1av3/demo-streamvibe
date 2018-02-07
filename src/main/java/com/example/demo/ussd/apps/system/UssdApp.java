package com.example.demo.ussd.apps.system;

import java.util.List;

public interface UssdApp {
    List<String> run(String from, String input);
}
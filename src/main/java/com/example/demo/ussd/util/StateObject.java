package com.example.demo.ussd.util;

import lombok.Data;

@Data
public class StateObject {


    StringPageObject pageObject;
    ListPageObject listPageObject;

    String userInput;

    public void setPageObject(StringPageObject stringPageObject){
        this.pageObject = stringPageObject;
    }


}
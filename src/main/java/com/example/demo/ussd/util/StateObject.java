package com.example.demo.ussd.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StateObject {


    private StringPageObject pageObject;
    private ListPageObject listPageObject;

    private String userInput;

    public void setPageObject(StringPageObject stringPageObject){
        this.pageObject = stringPageObject;
    }

}
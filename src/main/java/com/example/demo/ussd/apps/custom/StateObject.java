package com.example.demo.ussd.apps.custom;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavlovskii-pc on 07/02/2018.
 */
@Data
public class StateObject {

    List<String> pageable;
    Integer lastShownIndex;
    String data;


    public StateObject(String string) {
        int size = 15;

        List<String> parts = new ArrayList<>();

        int length = string.length();
        for (int i = 0; i < length; i += size) {
            parts.add(string.substring(i, Math.min(length, i + size)));
        }

        this.pageable=parts;
    }

    public String getCurrent(){
        return pageable.get(lastShownIndex);
    }

    public String getPrev(){
        lastShownIndex--;
        return pageable.get(lastShownIndex);
    }

    public String getNext(){
        lastShownIndex++;
        return pageable.get(lastShownIndex);
    }
}

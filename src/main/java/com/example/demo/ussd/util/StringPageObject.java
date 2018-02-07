package com.example.demo.ussd.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavlovskii-pc on 07/02/2018.
 */
@Data
public class StringPageObject {

    List<String> pageable;
    Integer lastShownIndex;
    String data;
    Integer size;

    final String prevPage="<7.";
    final String nextPage=".9>";
    final String quitPage="0";

    public StringPageObject(String string) {
        int size = 25;

        List<String> parts = new ArrayList<>();

        int length = string.length();
        for (int i = 0; i < length; i += size) {
            parts.add(string.substring(i, Math.min(length, i + size)));
        }

        this.pageable=parts;
        this.size=parts.size();
    }

    public String getCurrent(){
        if(lastShownIndex==null){
            lastShownIndex=0;
        }
        return pageable.get(lastShownIndex)+getControls();
    }

    public String getPrev(){

        if(lastShownIndex>0) {
            lastShownIndex--;
        }

        return pageable.get(lastShownIndex)+getControls();
    }

    public String getNext(){
        if(lastShownIndex<pageable.size()-1) {
            lastShownIndex++;
        }
        return pageable.get(lastShownIndex)+getControls();
    }

    private String getControls(){
        String controls="";
        if(!isFirst() && !isLast()){
            controls="\n"+prevPage+nextPage;
        }
        if(isFirst() && !isLast()){
            controls=quitPage+nextPage;
        }
        if(!isFirst() && isLast()){
            controls="\n"+prevPage+quitPage;
        }
        if(isFirst() && isLast()){

        }
        return controls;
    }

    boolean isFirst(){
        if(lastShownIndex==0){
            return true;
        }
        return false;
    }

    boolean isLast(){
        if(lastShownIndex+1==pageable.size()){
            return true;
        }
        return false;
    }
}

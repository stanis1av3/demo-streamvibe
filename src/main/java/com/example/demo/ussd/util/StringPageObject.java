package com.example.demo.ussd.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavlovskii-pc on 07/02/2018.
 */
@Data
@NoArgsConstructor
public class StringPageObject {

    private List<String> pageable;
    private Integer lastShownIndex;
    private String data;
    private Integer size;

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

    @JsonIgnore
    public String getCurrent(){
        if(lastShownIndex==null){
            lastShownIndex=0;
        }
      //  if(pageable==null) return null;
        return pageable.get(lastShownIndex)+getControls();
    }
    @JsonIgnore
    public String getPrev(){

        if(lastShownIndex>0) {
            lastShownIndex--;
        }

        return pageable.get(lastShownIndex)+getControls();
    }
    @JsonIgnore
    public String getNext(){
        if(lastShownIndex<pageable.size()-1) {
            lastShownIndex++;
        }
        return pageable.get(lastShownIndex)+getControls();
    }
    @JsonIgnore
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
    @JsonIgnore
    boolean isFirst(){
        if(lastShownIndex==0){
            return true;
        }
        return false;
    }
    @JsonIgnore
    boolean isLast(){

        if(lastShownIndex+1==pageable.size()){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "StringPageObject{" +
                "size=" + size +
                ", data='" + data + '\'' +
                ", lastShownIndex=" + lastShownIndex +
                ", pageable=" + pageable +
                '}';
    }
}

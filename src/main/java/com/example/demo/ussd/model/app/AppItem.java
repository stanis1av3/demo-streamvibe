package com.example.demo.ussd.model.app;

import lombok.Data;

import java.util.List;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Data
public class AppItem {

    Type type;

    String caption;

    AppItem parent;

    List<AppItem> childItems;


    public enum Type {
        MENU,
        APP_A,
        APP_B,
        APP_C,
        APP_X,
        APP_GO_BACK,
        APP_EXCHANGE_RATES,
        APP_ECONOMICS_NEWS;
    }

    @Override
    public String toString() {
        return "Item{" +
                "type=" + type +
                ", caption='" + caption + '\'' +
                '}';
    }
}

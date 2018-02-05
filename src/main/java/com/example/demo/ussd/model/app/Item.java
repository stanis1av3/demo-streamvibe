package com.example.demo.ussd.model.app;

import com.sun.deploy.security.ValidationState;
import lombok.Data;

import java.util.List;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Data
public class Item {

    Type type;

    String caption;

    Item parent;

    List<Item> childItems;


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

}

package com.example.demo.ussd.model.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Data
public class AppItem {

    Type type;

    String header;

    String caption;

    List<AppItem> childItems;


    public enum Type {
        MENU,
        NEWS_APP,
        EVENTS_APP,
        LICENSE_PRICE_PRIMA_APP,
        ABOUT_PRIMA_APP,
        LICENSE_REGISTRATION_SUPPORT_APP,
        LICENSE_PRICE_SECO_APP,
        ABOUT_SECO_APP,
        LICENSE_PRICE_SUPE_APP,
        ABOUT_SUPE_APP,
        ABOUT_DONATION_APP,
        DONATION_SUPPORT_APP,
        ABOUT_US_APP,
        ABOUT_PHONE_APP,
        ABOUT_EMAIL_APP,
        ABOUT_ADDRESS_APP
    }

}

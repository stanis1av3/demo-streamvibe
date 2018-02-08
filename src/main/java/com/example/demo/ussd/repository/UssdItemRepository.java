package com.example.demo.ussd.repository;

import com.example.demo.ussd.model.app.AppItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.sun.glass.ui.Menu;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Component
public class UssdItemRepository {

    AppItem root;

    public AppItem getRootItem() {
        return root;
    }

    @PostConstruct
    void started() {
        this.root = actualMenu();
    }

    public AppItem findParent(AppItem appItem) {
        List<AppItem> appItems = new ArrayList<>(root.getChildItems());
        AppItem parent = root;
        for (int i = 0; i < appItems.size(); i++) {
            if (appItems.get(i).getChildItems() == null) {
                continue;
            }
            if (appItems.stream().anyMatch(ai -> ai.equals(appItem))) {
                return parent;
            } else {
                parent = appItems.get(i);
            }
            appItems.addAll(appItems.get(i).getChildItems());

        }
        return null;
    }

//todo: a stub - to be moved to DB
    AppItem actualMenu() {
        AppItem root = new AppItem();

        root.setHeader("Welcome to OISSU APP");

        root.setType(AppItem.Type.MENU);

        AppItem newsItem = new AppItem();
        newsItem.setCaption("1 News");
        newsItem.setType(AppItem.Type.NEWS_APP);

        AppItem eventsItem = new AppItem();
        eventsItem.setCaption("2 Events");
        eventsItem.setType(AppItem.Type.EVENTS_APP);

        AppItem aboutMenuItem = new AppItem();
        aboutMenuItem.setCaption("3 About");
        aboutMenuItem.setType(AppItem.Type.MENU);

        AppItem oissuLicensPlansItem = new AppItem();
        oissuLicensPlansItem.setType(AppItem.Type.MENU);
        oissuLicensPlansItem.setCaption("4 License");

        AppItem donateItem = new AppItem();
        donateItem.setCaption("5 Donate");
        donateItem.setType(AppItem.Type.MENU);


        root.setChildItems(Lists.newArrayList(newsItem, eventsItem, aboutMenuItem, oissuLicensPlansItem, donateItem));

        AppItem primarieItem = new AppItem();
        primarieItem.setCaption("1 OISSU Enseignment Primarie");
        primarieItem.setType(AppItem.Type.MENU);

        AppItem secondarieItem = new AppItem();
        secondarieItem.setCaption("2 OISSU Enseignment Secondarie");
        secondarieItem.setType(AppItem.Type.MENU);

        AppItem superiorItem = new AppItem();
        superiorItem.setCaption("3 OISSU Enseignment Superiour");
        superiorItem.setType(AppItem.Type.MENU);

        oissuLicensPlansItem.setChildItems(Lists.newArrayList(primarieItem, secondarieItem, superiorItem));

        AppItem licensePricePr = new AppItem();
        licensePricePr.setCaption("1 License price");
        licensePricePr.setType(AppItem.Type.LICENSE_PRICE_PRIMA_APP);

        AppItem aboutPr = new AppItem();
        aboutPr.setCaption("2 About");
        aboutPr.setType(AppItem.Type.ABOUT_PRIMA_APP);

        AppItem registrationPr = new AppItem();
        registrationPr.setCaption("3 Registration support");
        registrationPr.setType(AppItem.Type.LICENSE_REGISTRATION_SUPPORT_APP);

        primarieItem.setChildItems(Lists.newArrayList(licensePricePr, aboutPr, registrationPr));

        AppItem aboutSe = new AppItem();
        aboutSe.setCaption("2 About");
        aboutSe.setType(AppItem.Type.ABOUT_SECO_APP);

        AppItem licensePriceSe = new AppItem();
        licensePriceSe.setType(AppItem.Type.LICENSE_PRICE_SECO_APP);
        licensePriceSe.setCaption("1 Licence price");

        AppItem registrationSe = new AppItem();
        registrationSe.setCaption("3 Registration support");
        registrationSe.setType(AppItem.Type.LICENSE_REGISTRATION_SUPPORT_APP);

        secondarieItem.setChildItems(Lists.newArrayList(licensePriceSe, aboutSe, registrationSe));

        AppItem licensePriceSu = new AppItem();
        licensePriceSu.setType(AppItem.Type.LICENSE_PRICE_SUPE_APP);
        licensePriceSu.setCaption("1 Licence price");

        AppItem aboutSu = new AppItem();
        aboutSu.setCaption("2 About");
        aboutSu.setType(AppItem.Type.ABOUT_SUPE_APP);

        AppItem registrationSu = new AppItem();
        registrationSu.setCaption("3 Registration support");
        registrationSu.setType(AppItem.Type.LICENSE_REGISTRATION_SUPPORT_APP);

        superiorItem.setChildItems(Lists.newArrayList(licensePriceSu, aboutSu, registrationSu));


        AppItem aboutDonation = new AppItem();
        aboutDonation.setCaption("1 About donation");
        aboutDonation.setType(AppItem.Type.ABOUT_DONATION_APP);

        AppItem donationSupport = new AppItem();
        donationSupport.setCaption("2 Donation support");
        donationSupport.setType(AppItem.Type.DONATION_SUPPORT_APP);

        donateItem.setChildItems(Lists.newArrayList(aboutDonation, donationSupport));

        AppItem aboutUsItem = new AppItem();
        aboutUsItem.setCaption("1 About us");
        aboutUsItem.setType(AppItem.Type.ABOUT_US_APP);

        AppItem aboutPhoneItem = new AppItem();
        aboutPhoneItem.setCaption("2 Phone");
        aboutPhoneItem.setType(AppItem.Type.ABOUT_PHONE_APP);

        AppItem aboutEmailItem = new AppItem();
        aboutEmailItem.setCaption("3 Email");
        aboutEmailItem.setType(AppItem.Type.ABOUT_EMAIL_APP);


        AppItem aboutAddressItem = new AppItem();
        aboutAddressItem.setCaption("4 Address");
        aboutAddressItem.setType(AppItem.Type.ABOUT_ADDRESS_APP);

        aboutMenuItem.setChildItems(Lists.newArrayList(aboutUsItem, aboutPhoneItem, aboutEmailItem, aboutAddressItem));


        return root;


    }
}

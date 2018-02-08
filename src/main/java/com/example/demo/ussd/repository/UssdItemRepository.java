package com.example.demo.ussd.repository;

import com.example.demo.ussd.model.app.AppItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.sun.glass.ui.Menu;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Component
public class UssdItemRepository {

    AppItem root;

    public AppItem getRootItem(){
        return root;
    }

    @PostConstruct
    void started(){
        AppItem root = actualMenu();

        this.root = root;
    }

    private AppItem fakeMenu() {
        AppItem root = new AppItem();

        root.setHeader("Welcome to OISSU APP");

        root.setType(AppItem.Type.MENU);

        AppItem itemA = new AppItem();
        itemA.setCaption("1 Currencies");
        itemA.setType(AppItem.Type.EXCHANGE_RATES_APP);

        AppItem itemB = new AppItem();
        itemB.setCaption("2 News");
        itemB.setType(AppItem.Type.MENU);

        AppItem itemC = new AppItem();
        itemC.setCaption("3 Weather");
        itemC.setType(AppItem.Type.MENU);

        AppItem itemQuit = new AppItem();

        itemQuit.setCaption("0 Quit");
        itemQuit.setType(AppItem.Type.GO_BACK_APP);

        root.setChildItems(Lists.newArrayList(itemA, itemB, itemC, itemQuit));

        AppItem back = new AppItem();
        back.setCaption("0 back");
        back.setType(AppItem.Type.GO_BACK_APP);

        AppItem itemBA = new AppItem();
        itemBA.setCaption("1 Local news");
        itemBA.setType(AppItem.Type.MENU);

        AppItem itemBB = new AppItem();
        itemBB.setCaption("2 Federal news");
        itemBB.setType(AppItem.Type.MENU);
        itemB.setChildItems(Lists.newArrayList(itemBA, itemBB, back));

        AppItem itemBBA = new AppItem();
        itemBBA.setCaption("1 Economics");
        itemBBA.setType(AppItem.Type.ECONOMICS_NEWS_APP);


        AppItem itemBBB = new AppItem();
        itemBBB.setCaption("2 Security");
        itemBBB.setType(AppItem.Type.APP_A);

        AppItem itemBBC = new AppItem();
        itemBBC.setCaption("3 Culture");
        itemBBC.setType(AppItem.Type.APP_B);

        AppItem itemBBD = new AppItem();
        itemBBD.setCaption("4 Sports");
        itemBBD.setType(AppItem.Type.APP_C);

        AppItem back3 = new AppItem();
        back3.setCaption("0 back");
        back3.setType(AppItem.Type.GO_BACK_APP);

        itemBB.setChildItems(Lists.newArrayList(itemBBA, itemBBB, itemBBC, itemBBD, back3));

        root.setCaption("Welcome to 'OISSU APP'");
        return root;
    }


    AppItem actualMenu(){
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

        ObjectMapper mapper = new ObjectMapper();


//Object to JSON in String
        try {
            String jsonInString = mapper.writeValueAsString(root);
            System.out.println(jsonInString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return root;



    }
}

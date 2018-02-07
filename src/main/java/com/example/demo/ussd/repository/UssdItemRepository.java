package com.example.demo.ussd.repository;

import com.example.demo.ussd.model.app.AppItem;
import com.google.common.collect.Lists;
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
        AppItem root = new AppItem();

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

        root.setCaption("hello!");

        this.root = root;
    }
}

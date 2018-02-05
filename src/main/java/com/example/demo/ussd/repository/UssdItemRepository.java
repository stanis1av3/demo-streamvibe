package com.example.demo.ussd.repository;

import com.example.demo.ussd.model.app.Item;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Component
public class UssdItemRepository {

    Item root;

    public Item getRootItem(){
        return root;
    }

    @PostConstruct
    void started(){
        Item root = new Item();

        root.setType(Item.Type.MENU);

        Item itemA = new Item();
        itemA.setCaption("1 Currencies");
        itemA.setType(Item.Type.APP_EXCHANGE_RATES);

        Item itemB = new Item();
        itemB.setCaption("2 News");
        itemB.setType(Item.Type.MENU);

        Item itemC = new Item();
        itemC.setCaption("3 Weather");
        itemC.setType(Item.Type.MENU);

        Item itemQuit = new Item();

        itemQuit.setCaption("0 Quit");
        itemQuit.setType(Item.Type.APP_GO_BACK);

        root.setChildItems(Lists.newArrayList(itemA, itemB, itemC, itemQuit));

        Item back = new Item();
        back.setCaption("0 back");
        back.setType(Item.Type.APP_GO_BACK);

        Item itemBA = new Item();
        itemBA.setCaption("1 Local news");
        itemBA.setType(Item.Type.MENU);

        Item itemBB = new Item();
        itemBB.setCaption("2 Federal news");
        itemBB.setType(Item.Type.MENU);
        itemB.setChildItems(Lists.newArrayList(itemBA, itemBB, back));

        Item itemBBA = new Item();
        itemBBA.setCaption("1 Economics");
        itemBBA.setType(Item.Type.APP_ECONOMICS_NEWS);


        Item itemBBB = new Item();
        itemBBB.setCaption("2 Security");
        itemBBB.setType(Item.Type.APP_A);

        Item itemBBC = new Item();
        itemBBC.setCaption("3 Culture");
        itemBBC.setType(Item.Type.APP_B);

        Item itemBBD = new Item();
        itemBBD.setCaption("4 Sports");
        itemBBD.setType(Item.Type.APP_C);

        itemBB.setChildItems(Lists.newArrayList(itemBBA, itemBBB, itemBBC, itemBBD, back));

        root.setCaption("hello!");
    }
}

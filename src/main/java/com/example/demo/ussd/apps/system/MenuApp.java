package com.example.demo.ussd.apps.system;

import com.example.demo.ussd.model.app.AppItem;
import com.example.demo.ussd.model.app.AppResponse;
import com.example.demo.ussd.repository.UssdItemRepository;
import com.example.demo.ussd.service.UssdSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("MENU")
public class MenuApp implements UssdApp {

    @Autowired
    UssdSessionService sessionService;

    @Autowired
    UssdItemRepository itemRepository;

    @Override
    public AppResponse run(String from, String input) {

        if(input.equals("")) {
            try {
                return new AppResponse(getCurrentItemMenu(from));

            } catch (Exception e) {
                return new AppResponse(getRootItemMenu());
            }
        }

        if(input.equals("0")){
            return new AppResponse(-1);
        }

        Integer index = parseInput(input);

        if(index!=null){
            return new AppResponse(index);
        }

        return new AppResponse(getRootItemMenu());
    }

    private List<String> getRootItemMenu(){
        return itemRepository.getRootItem().getChildItems()
                .stream()
                .map(AppItem::getCaption).collect(Collectors.toList());
    }

    private List<String> getCurrentItemMenu(String from){

        AppItem item = sessionService.getCurrent(from);
        return item.getChildItems()
                .stream()
                .map(AppItem::getCaption).collect(Collectors.toList());

    }





    private Integer parseInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return null;
        }
    }


}
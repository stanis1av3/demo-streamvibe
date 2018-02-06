package com.example.demo.ussd.service;

import com.example.demo.ussd.model.UssdMessageDTO;
import com.example.demo.ussd.model.app.AppItem;
import com.example.demo.ussd.repository.UssdItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Service
@Slf4j
public class UssdRootService {

    @Autowired
    UssdAppsService appsService;

    @Autowired
    UssdSessionService sessionService;

    AppItem root;

    @Autowired
    UssdItemRepository itemRepository;



    public UssdMessageDTO renderResponse(UssdMessageDTO messageDTO) {

        log.debug("Test message!");

        AppItem item = sessionService.getCurrent(messageDTO.getFrom());

        String inputMessage = messageDTO.getMessage();
        String from = messageDTO.getFrom();
        List<String> appResponse = new ArrayList();

        if (item.getType()== AppItem.Type.MENU) {
            appResponse = appsService.menuApp(from, inputMessage);
        }

        if (item.getType() == AppItem.Type.APP_GO_BACK) {
            appResponse = appsService.goBackApp(from);
        }

        if (item.getType()== AppItem.Type.APP_ECONOMICS_NEWS){
        }

        if(item.getType() == AppItem.Type.APP_EXCHANGE_RATES) {
            item = sessionService.getCurrent(from);
        }


        String preparedView = appResponse
                .stream().reduce((a,b)->a+"\n"+b).get();


        UssdMessageDTO responseMessageDTO = new UssdMessageDTO();
        responseMessageDTO.setTo(messageDTO.getFrom());
        responseMessageDTO.setMessage(preparedView);
        return responseMessageDTO;
    }



    private Integer convertStringMessageToInteger(String userMessage) {
        try {
            return Integer.parseInt(userMessage);
        } catch (Exception e) {
            return null;
        }
    }

    @PostConstruct
    void started(){
        root = itemRepository.getRootItem();
    }
}

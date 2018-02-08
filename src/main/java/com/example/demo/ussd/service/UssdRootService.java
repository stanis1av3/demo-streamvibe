package com.example.demo.ussd.service;

import com.example.demo.ussd.apps.system.UssdApp;
import com.example.demo.ussd.model.transport.UssdMessageDTO;
import com.example.demo.ussd.model.app.AppItem;
import com.example.demo.ussd.model.app.AppResponse;
import com.example.demo.ussd.repository.UssdItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Service
@Slf4j
public class UssdRootService {


    @Autowired
    UssdSessionService sessionService;

    AppItem root;

    @Autowired
    UssdItemRepository itemRepository;

    @Autowired
    ApplicationContext context;


    public UssdMessageDTO renderResponse(UssdMessageDTO messageDTO) {


        String input = messageDTO.getMessage();
        String from = messageDTO.getSid();

        AppItem currentItem = sessionService.getCurrent(from);

        List<String> appResponse = executeApp(from, input, currentItem.getType());

        String preparedView = appResponse
                .stream().reduce((a, b) -> a + "\n" + b).get();

        return new UssdMessageDTO(from, preparedView);
    }


    private List<String> executeApp(String from, String input, AppItem.Type appItemType) {

        UssdApp ussdApp = (UssdApp) context.getBean(appItemType.name());

        AppResponse view = ussdApp.init(from).run(from, input);

        //quit
        if(view.getOffset()<0){
            AppItem item = sessionService.goBack(from);
            ussdApp = (UssdApp) context.getBean(item.getType().name());
            List<String> body = ussdApp.run(from, input).getBody();
            ussdApp.destroy(from);
            return body;
        }
        //stay
        if(view.getOffset()==0) {
            return view.getBody();
        }
        //go forward
        if(view.getOffset()>0){
            AppItem item = sessionService.goForward(from, input);
            ussdApp = (UssdApp) context.getBean(item.getType().name());
            input="";
            return ussdApp.init(from).run(from, input).getBody();
        }

        return view.getBody();
    }

    @PostConstruct
    void started() {
        root = itemRepository.getRootItem();
    }
}

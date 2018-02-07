package com.example.demo.ussd.apps.transport;

import lombok.Data;

import java.util.List;

/**
 * Created by Pavlovskii-pc on 07/02/2018.
 */
@Data
public class AppResponsePageable {

    List<String> view;
    Integer currentPage;


}

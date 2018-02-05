package com.example.demo.ussd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pavlovskii-pc on 05/02/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UssdMessageDTO {
    String from;
    String to;
    String sid;
    String message;
}

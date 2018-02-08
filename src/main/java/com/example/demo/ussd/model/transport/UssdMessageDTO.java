package com.example.demo.ussd.model.transport;

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
    String msisdn;
    String to;
    String sid;
    String message;

    public UssdMessageDTO(String to, String message) {
        this.to=to;
        this.message=message;
    }
}

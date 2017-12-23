package com.example.demo.transport.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Pavlovskii-pc on 22/12/2017.
 */
@Data
@NoArgsConstructor
public class GetApiTokenRequest extends BaseRequest {

    GetApiTokenRequest.Params params;

    public GetApiTokenRequest(String accessKey, String vendorKey){
        params = new Params(accessKey, vendorKey);
        method=Method.getApiToken.name();
    }

    @Data
    @NoArgsConstructor
    public class Params {

        String accessKey;
        String vendorKey;

        public Params(String accessKey, String vendorKey) {
            this.accessKey = accessKey;
            this.vendorKey = vendorKey;
        }


    }

}

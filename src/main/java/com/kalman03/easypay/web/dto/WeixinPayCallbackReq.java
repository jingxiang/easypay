package com.kalman03.easypay.web.dto;
import lombok.Data;

@Data
public class WeixinPayCallbackReq  {

    private String id;
    private String create_time;
    private String event_type;
    private WechatPayResource resource;
    private String summary;

    @Data
    public static final class WechatPayResource {
        private String algorithm;
        private String ciphertext;
        private String nonce;
        private String original_type;
        private String associated_data;
    }
}
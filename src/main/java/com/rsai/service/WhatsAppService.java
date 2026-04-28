package com.rsai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;

@Service
public class WhatsAppService {

    @Value("${twilio.sid}")
    private String sid;

    @Value("${twilio.token}")
    private String token;

    @Value("${twilio.whatsapp}")
    private String from;

    @PostConstruct
    public void init() {
        Twilio.init(sid, token);
    }

    public void send(String to, String msg) {
    	System.out.println(to+"-"+msg);
        Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber(from),
                msg
        ).create();
    }
}
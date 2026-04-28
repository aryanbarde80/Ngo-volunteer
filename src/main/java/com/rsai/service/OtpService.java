package com.rsai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;
import com.rsai.model.Otp;
import com.rsai.repo.OtpRepo;

@Service
public class OtpService {

    @Autowired
    private OtpRepo repo;

    @Autowired
    private WhatsAppService whatsAppService;

    public String generate(String phone) {
        String code = String.valueOf(100000 + new Random().nextInt(900000));
        Otp o = repo.findByPhone(phone);
        if (o == null) o = new Otp();
        o.setPhone(phone);
        o.setCode(code);
        repo.save(o);

        // Send OTP via WhatsApp
        whatsAppService.send(phone, "Your RSAI Squad OTP is: " + code + "\nDo not share this with anyone.");

        return "OTP sent to " + phone;
    }

    public boolean verify(String phone, String code) {
        Otp o = repo.findByPhone(phone);
        return o != null && o.getCode().equals(code);
    }
}
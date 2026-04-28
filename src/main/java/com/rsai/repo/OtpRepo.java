package com.rsai.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rsai.model.Otp;

public interface OtpRepo extends JpaRepository<Otp, Long> {
    Otp findByPhone(String phone);
}
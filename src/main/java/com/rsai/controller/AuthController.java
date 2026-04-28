package com.rsai.controller;

import java.util.List;

import com.rsai.model.Volunteer;
import com.rsai.repo.VolunteerRepo;
import com.rsai.service.OtpService;
import com.rsai.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private VolunteerRepo repo;
	@Autowired
	private OtpService otpService;
	@Autowired
	private SessionService sessionService;

	@Value("${admin.email:admin@rsai.org}")
	private String adminEmail;

	@Value("${admin.password:admin123}")
	private String adminPassword;

	// ── SEND OTP ──────────────────────────────────────
	@PostMapping("/send-otp")
	public String sendOtp(@RequestParam String phone) {
		return otpService.generate(phone);
	}

	// ── REGISTER VOLUNTEER ────────────────────────────
	@PostMapping("/register")
	public String register(@RequestBody Volunteer v, @RequestParam String otp) {
		if (!otpService.verify(v.getPhone(), otp)) {
			return "Invalid OTP";
		}

		// Fix phone format
		String phone = v.getPhone().trim();
		if (!phone.startsWith("+"))
			phone = "+91" + phone;
		v.setPhone(phone);

		v.setVerified(true);
		v.setStatus("AVAILABLE");
		repo.save(v);
		return "Registered";
	}

	// ── LOGIN ─────────────────────────────────────────
	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password,
			@RequestParam(defaultValue = "volunteer") String role, HttpSession session) {

		System.out.println("=== LOGIN ATTEMPT ===");
		System.out.println("Email: [" + email + "]");
		System.out.println("Password: [" + password + "]");
		System.out.println("Role: [" + role + "]");

		// Admin login
		if ("admin".equals(role)) {
			if (adminEmail.equals(email) && adminPassword.equals(password)) {
				Volunteer admin = new Volunteer();
				admin.setName("Admin");
				admin.setEmail(adminEmail);
				admin.setStatus("ACTIVE");
				sessionService.createSession(session, admin, "ADMIN");
				return "ADMIN";
			}
			return "Invalid credentials";
		}

		// Debug — find all volunteers
		List<Volunteer> all = repo.findAll();
		System.out.println("All volunteers in DB: " + all.size());
		for (Volunteer v : all) {
			System.out.println("  DB email: [" + v.getEmail() + "] DB pass: [" + v.getPassword() + "]");
			System.out.println("  Match email: " + v.getEmail().equals(email.trim()));
			System.out.println("  Match pass:  " + v.getPassword().equals(password.trim()));
		}

		Volunteer v = repo.findByEmailAndPassword(email.trim(), password.trim());
		System.out.println("Found volunteer: " + v);

		if (v == null)
			return "Invalid credentials";
		if (!v.isVerified())
			return "Account not verified";

		sessionService.createSession(session, v, "VOLUNTEER");
		return "VOLUNTEER";
	}

	// ── LOGOUT ────────────────────────────────────────
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		sessionService.destroySession(session);
		return "Logged out";
	}

	@GetMapping("/logout") // handle GET too
	public void logoutGet(HttpSession session, jakarta.servlet.http.HttpServletResponse response)
			throws java.io.IOException {
		sessionService.destroySession(session);
		response.sendRedirect("/");
	}

	// ── CURRENT USER ──────────────────────────────────
	@GetMapping("/me")
	public Object me(HttpSession session) {
		if (!sessionService.isLoggedIn(session)) {
			return "Not logged in";
		}
		return sessionService.getLoggedUser(session);
	}
}
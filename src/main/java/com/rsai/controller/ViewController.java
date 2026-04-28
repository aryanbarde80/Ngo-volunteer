package com.rsai.controller;

import com.rsai.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {

	@Autowired
	private SessionService sessionService;

	@GetMapping("/")
	public String login(HttpSession session) {
		// Already logged in — redirect to correct page
		if (sessionService.isLoggedIn(session)) {
			return sessionService.isAdmin(session) ? "redirect:/dashboard" : "redirect:/volunteer-dashboard";
		}
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session) {
		if (!sessionService.isAdmin(session))
			return "redirect:/";
		return "dashboard";
	}

	@GetMapping("/volunteer")
	public String vol(HttpSession session) {
		if (!sessionService.isAdmin(session))
			return "redirect:/";
		return "volunteer";
	}

	@GetMapping("/admin")
	public String admin(HttpSession session) {
		if (!sessionService.isAdmin(session))
			return "redirect:/";
		return "admin";
	}

	@GetMapping("/volunteer-dashboard")
	public String volDashboard(HttpSession session) {
		if (!sessionService.isVolunteer(session))
			return "redirect:/";
		return "volunteer-dashboard";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    sessionService.destroySession(session);
	    return "redirect:/";
	}

	@PostMapping("/logout")
	public String logoutPost(HttpSession session) {
	    sessionService.destroySession(session);
	    return "redirect:/";
	
	}
}
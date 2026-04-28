package com.rsai.service;

import com.rsai.model.Volunteer;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

	private static final String USER_KEY = "loggedUser";
	private static final String ROLE_KEY = "userRole";

	public void createSession(HttpSession session, Volunteer v, String role) {
		session.setAttribute(USER_KEY, v);
		session.setAttribute(ROLE_KEY, role);
		session.setMaxInactiveInterval(60 * 60); // 1 hour timeout
	}

	public Volunteer getLoggedUser(HttpSession session) {
		return (Volunteer) session.getAttribute(USER_KEY);
	}

	public String getRole(HttpSession session) {
		String role = (String) session.getAttribute(ROLE_KEY);
		return role != null ? role : "";
	}

	public boolean isLoggedIn(HttpSession session) {
		return session.getAttribute(USER_KEY) != null;
	}

	public boolean isAdmin(HttpSession session) {
		return "ADMIN".equals(getRole(session));
	}

	public boolean isVolunteer(HttpSession session) {
		return "VOLUNTEER".equals(getRole(session));
	}

	public void destroySession(HttpSession session) {
		try {
			if (session != null) {
				session.invalidate();
			}
		} catch (IllegalStateException e) {
			// Session already invalidated — ignore
		}
	}
}
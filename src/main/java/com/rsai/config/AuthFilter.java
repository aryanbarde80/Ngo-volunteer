package com.rsai.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {

	// Routes that don't need login
	private static final String[] PUBLIC_ROUTES = { "/", "/register", "/auth/send-otp", "/auth/register", "/auth/login",
			"/auth/logout", "/logout", // ← add these
			"/accept/", "/reject/" };

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);

		String path = request.getRequestURI();

		// Always allow public routes
		for (String pub : PUBLIC_ROUTES) {
			if (path.equals(pub) || path.startsWith(pub)) {
				chain.doFilter(req, res);
				return;
			}
		}

		// Allow static resources
		if (path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")) {
			chain.doFilter(req, res);
			return;
		}

		// Check session exists
		if (session == null || session.getAttribute("loggedUser") == null) {
			response.sendRedirect("/");
			return;
		}

		String role = (String) session.getAttribute("userRole");

		// Admin-only route protection
		if (path.startsWith("/dashboard") || path.startsWith("/admin") || path.startsWith("/api/")
				|| path.equals("/volunteer")) {
			if (!"ADMIN".equals(role)) {
				response.sendRedirect("/volunteer-dashboard");
				return;
			}
		}

		// Volunteer-only route protection
		if (path.startsWith("/volunteer-dashboard")) {
			if (!"VOLUNTEER".equals(role)) {
				response.sendRedirect("/dashboard");
				return;
			}
		}

		chain.doFilter(req, res);
	}
}
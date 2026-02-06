package com.learningplatform.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.learningplatform.model.User;

public class ServletUtils {
    public static final String USER_SESSION_KEY = "user";
    public static final String LEARNED_SUBJECTS_COOKIE = "learned_subjects";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String SUCCESS_MESSAGE = "successMessage";

    /**
     * Get current user from session
     */
    public static User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute(USER_SESSION_KEY);
        }
        return null;
    }

    /**
     * Set current user in session
     */
    public static void setCurrentUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(USER_SESSION_KEY, user);
    }

    /**
     * Check if user is logged in
     */
    public static boolean isUserLoggedIn(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    }

    /**
     * Sanitize input to prevent XSS
     */
    public static String sanitizeInput(String input) {
        if (input == null) return null;
        return input.replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;")
                   .replace("&", "&amp;");
    }

    /**
     * Set error message in request
     */
    public static void setErrorMessage(HttpServletRequest request, String message) {
        request.setAttribute(ERROR_MESSAGE, message);
    }

    /**
     * Set success message in request
     */
    public static void setSuccessMessage(HttpServletRequest request, String message) {
        request.setAttribute(SUCCESS_MESSAGE, message);
    }
}

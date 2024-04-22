package com.javasecurity;

import com.javasecurity.entities.User;

public class LoginSession {
    private static User session;

    public static void setSession(User user) {
        session = user;
    }
    public static User getSession() {
        return session;
    }
}

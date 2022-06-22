package com.example.ftc.customer.utils;

import com.example.ftc.customer.domain.User;
import javax.servlet.http.HttpServletRequest;

public class ServerUtils {

    public static User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    public static Long getSessionUserId(HttpServletRequest request) {
        return getSessionUser(request).getId();
    }
}

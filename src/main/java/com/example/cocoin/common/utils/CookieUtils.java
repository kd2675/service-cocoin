package com.example.cocoin.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
public class CookieUtils {
    private static final String LOCAL_DOMAIN = "localhost";
    private static final String CONTEXT_DOMAIN = "";

    public static void createCookie(String name, String value) {
        HttpServletResponse res = HttpUtils.getResponse();

        try {
            value = URLEncoder.encode(value, "UTF-8");

            log.error("create cookie");
            Cookie c = new Cookie(name, value);
            c.setPath("/");
            c.setMaxAge(60 * 60 * 24 * 1);
//            c.setComment(name);
            c.setHttpOnly(true);
            c.setDomain(CONTEXT_DOMAIN);
            res.addCookie(c);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createCookie(String name, String value, int maxAge) {
        HttpServletResponse res = HttpUtils.getResponse();

        try {
            value = URLEncoder.encode(value, "UTF-8");

            log.error("create cookie");
            Cookie c = new Cookie(name, value);
            c.setPath("/");
            c.setMaxAge(maxAge);
//            c.setComment(name);
            c.setHttpOnly(true);
            c.setDomain(CONTEXT_DOMAIN);
            res.addCookie(c);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteCookie(String name) {
        HttpServletResponse res = HttpUtils.getResponse();

        log.error("delete cookie");
        Cookie c = new Cookie(name, "");
        c.setPath("/");
        c.setMaxAge(0);
//        c.setComment(name);
        c.setHttpOnly(true);
        c.setDomain(CONTEXT_DOMAIN);
        res.addCookie(c);
        //testtesttest
    }
}

package com.example.cocoin.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpUtils {
    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

    //Request 객체 얻기
    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    //Set Request Attribute
    public static void setRequestAttributes(String key, Object obj) {
        getServletRequestAttributes().setAttribute(key, obj, ServletRequestAttributes.SCOPE_REQUEST);
    }

    //Get Request Attribute
    public static Object getRequestAttributes(String key) {
        return getServletRequestAttributes().getAttribute(key, ServletRequestAttributes.SCOPE_REQUEST);
    }

    //HttpServletResponse 객체 얻기
    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    //Session 객체 얻기
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    //Set Session Attributes
    public static void setSessionAttributes(String key, Object obj) {
        getServletRequestAttributes().setAttribute(key, obj, ServletRequestAttributes.SCOPE_SESSION);
    }

    //Get Session Attributes
    public static void getSessionAttributes(String key) {
        getServletRequestAttributes().getAttribute(key, getServletRequestAttributes().SCOPE_SESSION);
    }

    //beanName을 통해서 Bean을 얻을 수 있다.
    public static Object getBean(String beanName) {
        return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
    }


}

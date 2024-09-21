package com.qaq.base.utils;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class MutableHttpServletRequest extends HttpServletRequestWrapper {
    
    private final Map<String, String> customHeaders;

    public MutableHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }

    public void putHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        // 检查是否存在自定义头部，如果有，返回该头部值
        String headerValue = customHeaders.get(name);
        
        if (headerValue != null) {
            return headerValue;
        }
        // 否则返回原始请求的头部值
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        // 创建一个集合合并原始请求的头部和自定义的头部
        HashSet<String> set = new HashSet<>(customHeaders.keySet());
        
        // 将原始的头部也添加到集合中
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            String n = e.nextElement();
            set.add(n);
        }
        // 返回集合的枚举
        return Collections.enumeration(set);
    }
}
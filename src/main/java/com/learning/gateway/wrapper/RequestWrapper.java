/**
package com.api.gateway.wrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class RequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> headers = new HashMap<>();
    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = headers.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> names = new HashSet<>(headers.keySet());
        Enumeration<String> originalHeaderNames = super.getHeaderNames();
        while (originalHeaderNames.hasMoreElements()) {
            names.add(originalHeaderNames.nextElement());
        }
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (headers.containsKey(name)) {
            return Collections.enumeration(Collections.singletonList(headers.get(name)));
        }
        return super.getHeaders(name);
    }
}
 **/

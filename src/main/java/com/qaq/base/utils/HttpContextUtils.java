package com.qaq.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletRequest;

/**
 * HttpContextUtils
 *
 * @author itdragons
 */
public class HttpContextUtils {

    // /**
    // * 获取query参数
    // *
    // * @param request
    // * @return
    // */
    // public static Map<String, String> getParameterMapAll(HttpServletRequest
    // request) {
    // var parameters = request.getParameterNames();
    // var params = new HashMap<String, String>();
    // while (parameters.hasMoreElements()) {
    // String parameter = parameters.nextElement();
    // String value = request.getParameter(parameter);
    // params.put(parameter, value);
    // }
    // return params;
    // }

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    // @Bean
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (var inputStream = request.getInputStream();
                var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

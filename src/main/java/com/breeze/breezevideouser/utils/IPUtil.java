package com.breeze.breezevideouser.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author breeze
 * @date 2023/3/15 09:54
 */
public class IPUtil {

    private static final String[] IP_HEADERS = {"X-Real-IP", "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP"};
    private static final String LOCAL_HOST = "127.0.0.1";

    /**
     * 获取ip地址
     * @param request 请求
     * @return ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ipAddress;
        try {
            for (String ipHeader : IP_HEADERS) {
                ipAddress = request.getHeader(ipHeader);
                if (ipAddress != null && ipAddress.length() != 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
                    return ipAddress;
                }
            }
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals(LOCAL_HOST)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (inet != null) {
                    ipAddress = inet.getHostAddress();
                }
            }

            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }

        return ipAddress;
    }
}

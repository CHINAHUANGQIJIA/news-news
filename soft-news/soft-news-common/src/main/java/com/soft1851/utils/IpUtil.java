package com.soft1851.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: IP宸ュ叿绫�
 * @author: mqxu
 * @create: 2020-11-15
 **/
public class IpUtil {

    /**
     * 鑾峰彇璇锋眰IP:
     * 鐢ㄦ埛鐨勭湡瀹濱P涓嶈兘浣跨敤request.getRemoteAddr()
     * 杩欐槸鍥犱负鍙兘浼氫娇鐢ㄤ竴浜涗唬鐞嗚蒋浠讹紝杩欐牱ip鑾峰彇灏变笉鍑嗙‘浜�
     * 姝ゅ鎴戜滑濡傛灉浣跨敤浜嗗绾э紙LVS/Nginx锛夊弽鍚戜唬鐞嗙殑璇濓紝ip闇€瑕佷粠X-Forwarded-For涓幏寰楃涓€涓潪unknown鐨処P鎵嶆槸鐢ㄦ埛鐨勬湁鏁坕p銆�
     *
     * @param request 璇锋眰
     * @return String
     */
    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
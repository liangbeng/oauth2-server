package org.wzp.oauth2.util;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.wzp.oauth2.config.CustomConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/21 10:45
 */
public class IpUtil {


    /**
     * 获取客户端Detail信息
     * 包括客户端ip地址 用户token等信息
     * IP地址 remoteAddress
     * token值 tokenValue
     * token头 tokenType
     *
     * @return
     */
    public static Map getDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object details = authentication.getDetails();
        //反射获取值
        Class jsonClass = details.getClass();
        // 得到类中的所有属性集合
        Field[] fs = jsonClass.getDeclaredFields();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); //设置属性为可以访问的
            try {
                // 得到此属性的值
                Object val = f.get(details);
                if (!StringUtils.isEmpty(val)) {
                    map.put(f.getName(), val.toString());
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    /**
     * 获取客户端ip地址
     *
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip) && ip.indexOf(",") != -1) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            ip = ip.split(",")[0];
        }
        if (checkIp(ip)) {
            ip = request.getRemoteAddr();
        }
        if (checkIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (checkIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (checkIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (checkIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (checkIp(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        return ip;
    }

    private static boolean checkIp(String ip) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            return true;
        }
        return false;
    }


    /**
     * 获取ip地址所在的省
     *
     * @param ip
     * @return
     */
    public static String getProvince(String ip) {
        String[] splitIpString = splitIpString(ip);
        String province = splitIpString[2].replaceAll("\\|", "");
        return province;
    }


    /**
     * 获取ip地址所在的市
     *
     * @param ip
     * @return
     */
    public static String getCity(String ip) {
        String[] splitIpString = splitIpString(ip);
        String city = splitIpString[3].replaceAll("\\|", "");
        return city;
    }


    /**
     * 对ip进行相应处理
     *
     * @param ip
     * @return
     */
    public static String[] splitIpString(String ip) {
        String cityIpString = getCityInfo(ip);
        String[] splitIpString = cityIpString.split("\\|");
        return splitIpString;
    }

    public static String getCityInfo(String ip) {
        //db文件下载地址，https://gitee.com/lionsoul/ip2region/tree/master/data 下载下来后解压，db文件在data目录下
        String dbPath = CustomConfig.ipData;
        File file = new File(dbPath);
        if (file.exists() == false) {
            System.out.println("Error: Invalid ip2region.db file");
        }
        //查询算法 B-tree
        int algorithm = DbSearcher.BTREE_ALGORITHM;
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbPath);
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }
            if (Util.isIpAddress(ip) == false) {
                System.out.println("Error: Invalid ip address");
            }
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取客户端的公网ip
     *
     * @param ip
     * @return
     */
    public static String publicNetWork(String ip) {
        String publicNetWork = CustomConfig.publicNetWork;
        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(publicNetWork);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while ((read = in.readLine()) != null) {
                inputLine.append(read + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            String ipStr = m.group(1);
            System.out.println(ipStr);
            return ipStr;
        }
        return null;
    }

}

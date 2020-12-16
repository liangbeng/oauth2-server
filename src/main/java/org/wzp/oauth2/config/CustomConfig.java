package org.wzp.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/28 17:19
 */
@Order(2)
@Component
@ConfigurationProperties(prefix = "custom-config")
public class CustomConfig {

    public static String withClient;
    public static String secret;
    public static String resourceId;
    public static String zipExe;
    public static String fileSave;
    public static String tokenUrl;
    public static String ipData;
    public static String defaultPassword;
    public static String publicNetWork;
    public static String emailExchange;
    public static String routingKey;

    public String getWithClient() {
        return withClient;
    }

    public void setWithClient(String withClient) {
        CustomConfig.withClient = withClient;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        CustomConfig.secret = secret;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        CustomConfig.resourceId = resourceId;
    }

    public String getZipExe() {
        return zipExe;
    }

    public void setZipExe(String zipExe) {
        CustomConfig.zipExe = zipExe;
    }

    public String getFileSave() {
        return fileSave;
    }

    public void setFileSave(String fileSave) {
        CustomConfig.fileSave = fileSave;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        CustomConfig.tokenUrl = tokenUrl;
    }

    public String getIpData() {
        return ipData;
    }

    public void setIpData(String ipData) {
        CustomConfig.ipData = ipData;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        CustomConfig.defaultPassword = defaultPassword;
    }

    public String getPublicNetWork() {
        return publicNetWork;
    }

    public void setPublicNetWork(String publicNetWork) {
        CustomConfig.publicNetWork = publicNetWork;
    }

    public String getEmailExchange() {
        return emailExchange;
    }

    public void setEmailExchange(String emailExchange) {
        CustomConfig.emailExchange = emailExchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        CustomConfig.routingKey = routingKey;
    }
}

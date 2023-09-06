package com.webank.ddcms.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.webank.ddcms.config.ThirdPartyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 第三方账号工具类
 * @author ashinnotfound
 * @date 2023/09/06
 */
@Component
public class ThirdPartyUtils {

    @Autowired
    private ThirdPartyConfig thirdPartyConfig;

    /**
     * 根据code获取github用户信息
     * @param code 授权github登陆后github颁发的临时code 有效期10min
     * @return {@code JsonObject}
     */
    public JsonObject getGithubAccountInfo(String code) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        //获取accessToken
        HttpRequest accessTokenRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://github.com/login/oauth/access_token"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "code=" + code +
                                "&client_id=" + thirdPartyConfig.getGithubClientId() +
                                "&client_secret=" + thirdPartyConfig.getGithubClientSecret()))
                .build();
        HttpResponse<String> accessTokenResponse = httpClient.send(accessTokenRequest, HttpResponse.BodyHandlers.ofString());
        JsonObject accessTokenJson = JsonParser.parseString(accessTokenResponse.body()).getAsJsonObject();
        String accessToken = accessTokenJson.get("access_token").getAsString();

        //获取账号信息
        HttpRequest accountInfoRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create("https://api.github.com/user"))
                .GET()
                .build();
        HttpResponse<String> accountInfoResponse = httpClient.send(accountInfoRequest, HttpResponse.BodyHandlers.ofString());
        return JsonParser.parseString(accountInfoResponse.body()).getAsJsonObject();
    }
}

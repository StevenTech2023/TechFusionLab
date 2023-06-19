package com.techfusionlab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author steven
 */
@RestController
@RequestMapping("/api")
@PropertySource("classpath:privacy.properties")
public class MyController {


    @Value("${password}")
    private String password;

    @Value("${clashUrl}")
    private String clashUrl;

    public static final String FLGA_CLASH = "clash";

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World! Branch Test";
    }

    @GetMapping("/v1/client/subscribe")
    public ResponseEntity<String> getFileContent(@RequestParam("token") String token, @RequestParam("flag") String flag) throws IOException {
        if (!password.equals(token)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("");
        }

//        if (FLGA_CLASH.equals(flag)) {
//            String content = sendHttpRequest(clashUrl);
//            return ResponseEntity.ok()
//                    .contentType(MediaType.TEXT_HTML)
//                    .body(content);
//        }

        if (FLGA_CLASH.equals(flag)) {
            String filePath = "/file/clash-subscribe.yaml";
            Resource resource = new ClassPathResource(filePath);
            String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(content);
        }


        String filePath = "/file/shadowrocket-subscribe.yaml";
        Resource resource = new ClassPathResource(filePath);
        String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        content = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
        // 返回内容作为响应
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(content);
    }

    private String sendHttpRequest(String url) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                    response.append("\n");
                }
                return response.toString();
            } else {
                // 处理请求失败的情况
                return "";
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}

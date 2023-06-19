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

import java.io.IOException;
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

    public static final String FLAG_CLASH = "clash";

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/v1/client/subscribe")
    public ResponseEntity<String> getFileContent(@RequestParam("token") String token, @RequestParam("flag") String flag) throws IOException {
        if (!password.equals(token)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("");
        }

        // 构建资源路径
        String fileName = "shadowrocket-subscribe.yaml";
        if (FLAG_CLASH.equals(flag)) {
            fileName = "clash-subscribe.yaml";
        }
        String filePath = "/file/" + fileName;
        // 从资源文件中获取内容
        Resource resource = new ClassPathResource(filePath);
        String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        if (!FLAG_CLASH.equals(flag)) {
            content = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
        }
        // 返回内容作为响应
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(content);
    }
}

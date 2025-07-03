package net.javaf.SecurityOauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IpController {

    @GetMapping("/ip")
    public String getIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        String xRealIp = request.getHeader("X-Real-IP");
        String xOriginalForwardedFor = request.getHeader("X-Original-Forwarded-For");
        return "X-Forwarded-For: " + xForwardedFor + "\\\\nX-Real-IP: " + xRealIp + "\\\\nX-Original-Forwarded-For: " + xOriginalForwardedFor;
    }
}

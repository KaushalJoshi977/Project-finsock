package com.example.finsock.project.Controllers;


import com.example.finsock.project.Models.UserActivity;
import com.example.finsock.project.Models.UserEntity;
import com.example.finsock.project.Repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/userDetails")
    public String createStudent(HttpServletRequest request, Model model){


        String userAgent = request.getHeader("User-Agent");
        String browserName = "Unknown";
        String osName = "Unknown";

        if (userAgent != null) {
            if (userAgent.contains("MSIE")) {
                browserName = "Internet Explorer";
            } else if (userAgent.contains("Firefox")) {
                browserName = "Mozilla Firefox";
            } else if (userAgent.contains("Chrome")) {
                browserName = "Google Chrome";
            } else if (userAgent.contains("Safari")) {
                browserName = "Apple Safari";
            } else if (userAgent.contains("Opera")) {
                browserName = "Opera";
            }

            Pattern pattern = Pattern.compile("\\((.*?)\\)");
            Matcher matcher = pattern.matcher(userAgent);
            while (matcher.find()) {
                String info = matcher.group(1);
                if (info.contains("Windows")) {
                    osName = "Windows";
                } else if (info.contains("Mac OS X")) {
                    osName = "Mac OS X";
                } else if (info.contains("Linux")) {
                    osName = "Linux";
                }
            }
        }

        WebClient ipClient = WebClient.create("https://api.ipify.org");
        String ip = ipClient.get()

                .retrieve().bodyToMono(String.class).block();


        WebClient client = WebClient
                .builder()
                .baseUrl("http://ip-api.com/json/" + ip)
                .build();
        UserEntity.IpResponse response = client.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserEntity.IpResponse.class)
                .block();
        LocalDateTime loginTime = LocalDateTime.now();

        UserActivity userActivity = new UserActivity();
        userActivity.setBrowserName(browserName);
        userActivity.setCity(response.city());
        userActivity.setCountry(response.country());
        userActivity.setLoginTime(loginTime.toString());
        userActivity.setId(UUID.randomUUID().toString());
        userActivity.setServiceProvider(response.isp());
        userActivity.setOsName(osName);

        userRepository.save(userActivity);

        return  "UserID = " + userActivity.getId() + "<br>" +
                "City name = " + response.city()  + "<br>"+
                " Country name = " + response.country() + "<br>" +
                "Service provider = " + response.isp() + "<br>" +
                "Browser name = " + browserName + "<br>" +
                "OS name = " + osName + "<br>" +
                "Login time = " + loginTime;
    }


    @GetMapping("/allUsers")
    public List<UserActivity> allUser(){
        List<UserActivity> userActivityList = userRepository.findAll();
        return userActivityList;
    }
}

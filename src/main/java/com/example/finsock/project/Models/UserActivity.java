package com.example.finsock.project.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "userActivity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserActivity {
    @Id
    private String id;

    private String serviceProvider;

    private String city;

    private String loginTime;

    private String osName;

    private String browserName;

    private String country;


}

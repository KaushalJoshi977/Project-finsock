package com.example.finsock.project.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class UserEntity {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record IpResponse(
            String country,
            String city,

            String isp
    ) {
    }
}

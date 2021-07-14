package com.example.jwttoken.model;

import com.nimbusds.jose.shaded.json.JSONObject;

import lombok.Data;

@Data
public class naverDto {
    private String resultcode;
    private String message;
    private JSONObject response;
}
